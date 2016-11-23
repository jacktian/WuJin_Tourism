package wujin.tourism.android.childactivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.plugin.radar.RadarView;
import com.beyondar.android.plugin.radar.RadarWorldPlugin;
import com.beyondar.android.util.ImageUtils;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.BeyondarObjectList;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.google.gson.JsonParseException;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import wujin.tourism.android.MyAppaplication;
import wujin.tourism.android.R;
import wujin.tourism.android.common.NewArbean;
import wujin.tourism.android.data.PlistHandler;

public class NewARActivity extends FragmentActivity implements OnClickBeyondarObjectListener {
	private static final String TMP_IMAGE_PREFIX = "viewImage_";
	private BeyondarFragmentSupport mBeyondarFragment;
	private RadarView mRadarView;
	private RadarWorldPlugin mRadarPlugin;
	private World mWorld;
	private MyAppaplication appaplication;
	private LocationManager mLocationManager;
	private String mLocationProvider;
	private double latitude;
	private double longitude;
	private ArrayList<NewArbean> newArrayList = new ArrayList<NewArbean>();
	private String id = "";
	private Timer timer;
	private Double minlat = 0.0;
	private Double maxlat = 0.0;
	private Double minlong = 0.0;
	private Double maxlong = 0.0;
	private int LIST_TYPE_EXAMPLE_1 = 1;
	private World sharedWorld;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cleanTempFolder();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.simple_camera_with_radar);
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		mLocationProvider = mLocationManager.getBestProvider(criteria, true);
		appaplication = (MyAppaplication) getApplication();
		appaplication.getInstance().addActivity(this);
		mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager()
				.findFragmentById(R.id.beyondarFragment);
		mRadarView = (RadarView) findViewById(R.id.radarView);
		mBeyondarFragment.setOnClickBeyondarObjectListener(this);
		mRadarPlugin = new RadarWorldPlugin(this);
		mRadarPlugin.setRadarView(mRadarView);
		mRadarPlugin.setMaxDistance(100);
		mRadarPlugin.setListColor(LIST_TYPE_EXAMPLE_1, Color.TRANSPARENT);
		mRadarPlugin.setListDotRadius(LIST_TYPE_EXAMPLE_1, Color.TRANSPARENT);
		try {
			id = this.getIntent().getExtras().getString("id");
		} catch (Exception e) {
		}
		new Thread(new Runnable() {
			public void run() {
				Looper.prepare();
				try {
					AssetManager assetManager = getApplicationContext().getAssets();
					InputStream is = assetManager.open("ardatas/ardata_" + id + ".plist");
					SAXParserFactory factorys = SAXParserFactory.newInstance();
					SAXParser saxparser = factorys.newSAXParser();
					PlistHandler plistHandler = new PlistHandler();
					saxparser.parse(is, plistHandler);
					ArrayList<Object> array = (ArrayList<Object>) plistHandler.getArrayResult();
					for (int i = 0; i < array.size(); i++) {
						HashMap<String, Object> map = (HashMap<String, Object>) array.get(i);
						String dpu = map.get("coord").toString().replace("{", "").replace("}", "");
						String a[] = dpu.split(",");
						newArrayList.add(
								new NewArbean(map.get("title").toString(), Double.valueOf(a[0]), Double.valueOf(a[1])));
					}
					if (mLocationProvider != null) {
						try {
							updateLocation(mLocationManager.getLastKnownLocation(mLocationProvider));
							mLocationManager.requestLocationUpdates(mLocationProvider, 0, 10, mLocationListener);
						} catch (Exception e) {
						}
					}
				} catch (JsonParseException e) {
				} catch (Exception e) {
				}
				Looper.loop();
			}
		}).start();
	}

	protected void onPause() {
		super.onPause();
		if (mLocationProvider != null) {
			mLocationManager.removeUpdates(mLocationListener);
		}
		if (timer != null) {
			timer.cancel();
		}
	}

	protected void onResume() {
		super.onResume();
	}

	private void updateLocation(Location location) {
		if (location == null) {
			Toast.makeText(NewARActivity.this, "没有获取到你所在的地理位置,请检查gps的可用性", Toast.LENGTH_SHORT).show();
			if (latitude > 0) {
			} else {
				this.finish();
			}
		} else {
			if (newArrayList.size() > 0) {
				try {
					latitude = location.getLatitude();
					longitude = location.getLongitude();
					Collections.sort(newArrayList, new StuComparator());
					maxlat = newArrayList.get(0).getLatitude();
					minlat = newArrayList.get(newArrayList.size() - 1).getLatitude();
					Collections.sort(newArrayList, new StuComparator1());
					maxlong = newArrayList.get(0).getLongitude();
					minlong = newArrayList.get(newArrayList.size() - 1).getLongitude();
					if (minlat < latitude && latitude < maxlat && minlong < longitude && longitude < maxlong) {
						mWorld = generateObjects(this, latitude, longitude, newArrayList);
					} else {
						if (latitude > maxlat && longitude < minlong) {
							mWorld = generateObjects(this, maxlat + 0.004504505, minlong - 0.004504505, newArrayList);
						} else if (latitude > maxlat && longitude < maxlong && latitude > minlong) {
							mWorld = generateObjects(this, maxlat + 0.004504505, minlong + 0.004504505, newArrayList);
						} else if (latitude > maxlat && longitude > maxlong) {
							mWorld = generateObjects(this, maxlat + 0.004504505, maxlong + 0.004504505, newArrayList);
						} else if (latitude < maxlat && longitude > maxlong && latitude > minlat) {
							mWorld = generateObjects(this, maxlat - 0.004504505, maxlong + 0.004504505, newArrayList);
						} else if (latitude < minlat && longitude > maxlong) {
							mWorld = generateObjects(this, minlat - 0.004504505, maxlong + 0.004504505, newArrayList);
						} else if (latitude < minlat && longitude > minlong && longitude < maxlong) {
							mWorld = generateObjects(this, minlat - 0.004504505, maxlong - 0.004504505, newArrayList);
						} else if (latitude < minlat && longitude < minlong) {
							mWorld = generateObjects(this, minlat - 0.004504505, minlong - 0.004504505, newArrayList);
						} else {
							mWorld = generateObjects(this, minlat + 0.004504505, minlong - 0.004504505, newArrayList);
						}

					}
					mBeyondarFragment.setWorld(mWorld);
					mWorld.addPlugin(mRadarPlugin);
					replaceImagesByStaticViews(mWorld);
				} catch (Exception e) {
				}
			}
		}
	}

	private World generateObjects(Context context, double latitude, double longitude,
			ArrayList<NewArbean> newArrayList) {
		if (sharedWorld != null) {
			return sharedWorld;
		}
		try {
			sharedWorld = new World(context);
			sharedWorld.setGeoPosition(Double.valueOf(String.valueOf(latitude).replace(".", ".0")),
					Double.valueOf(String.valueOf(longitude).replace(".", ".0")));
			for (int i = 0; i < newArrayList.size(); i++) {
				GeoObject go1 = new GeoObject(new Long((long) i));
				go1.setGeoPosition(Double.valueOf(String.valueOf(newArrayList.get(i).getLatitude()).replace(".", ".0")),
						Double.valueOf(String.valueOf(newArrayList.get(i).getLongitude()).replace(".", ".0")));
				go1.setImageResource(R.drawable.ic_launcher);
				go1.setName(newArrayList.get(i).getNameString());
				sharedWorld.addBeyondarObject(go1);
			}
		} catch (Exception e) {
		}
		return sharedWorld;
	}

	class StuComparator implements Comparator<NewArbean> {
		public int compare(NewArbean o1, NewArbean o2) {
			if (o1.getLatitude() < o2.getLongitude()) {
				return -1;
			} else if (o1.getLatitude() == o2.getLatitude()) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	class StuComparator1 implements Comparator<NewArbean> {
		public int compare(NewArbean o1, NewArbean o2) {
			if (o1.getLongitude() < o2.getLongitude()) {
				return -1;
			} else if (o1.getLongitude() == o2.getLongitude()) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			if (status != LocationProvider.OUT_OF_SERVICE) {
				updateLocation(mLocationManager.getLastKnownLocation(mLocationProvider));
			} else {
			}
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
			updateLocation(location);
		}
	};

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "校正指南针教程");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent intent = new Intent();
			intent.setClass(NewARActivity.this, JtitemActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("title", "指南针教程");
			bundle.putString("url", "file:///android_asset/jiaozhui.htm");
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void replaceImagesByStaticViews(World world) {
		String path = getTmpPath();
		for (BeyondarObjectList beyondarList : world.getBeyondarObjectLists()) {
			for (BeyondarObject beyondarObject : beyondarList) {
				View view = getLayoutInflater().inflate(R.layout.static_beyondar_object_view, null);
				TextView textView = (TextView) view.findViewById(R.id.geoObjectName);
				TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
				for (NewArbean newArbean : newArrayList) {
					if (newArbean.getNameString().equals(beyondarObject.getName())) {
						titleTextView.setText(String.valueOf(
								gps2m(latitude, longitude, newArbean.getLatitude(), newArbean.getLongitude())) + "米");
						break;
					}
				}
				textView.setText(beyondarObject.getName());
				try {
					String imageName = TMP_IMAGE_PREFIX + beyondarObject.getName() + ".png";
					ImageUtils.storeView(view, path, imageName);
					beyondarObject.setImageUri(path + imageName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private final double EARTH_RADIUS = 6378137.0;

	private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private String getTmpPath() {
		return getExternalFilesDir(null).getAbsoluteFile() + "/tmp/";
	}

	private void cleanTempFolder() {
		File tmpFolder = new File(getTmpPath());
		if (tmpFolder.isDirectory()) {
			String[] children = tmpFolder.list();
			for (int i = 0; i < children.length; i++) {
				if (children[i].startsWith(TMP_IMAGE_PREFIX)) {
					new File(tmpFolder, children[i]).delete();
				}
			}
		}
	}

	@Override
	public void onClickBeyondarObject(ArrayList<BeyondarObject> beyondarObjects) {
		if (beyondarObjects.size() > 0) {
			double longitude = 0;
			double latitude = 0;
			for (NewArbean newArbean : newArrayList) {
				if (newArbean.getNameString().equals(beyondarObjects.get(0).getName())) {
					longitude = newArbean.getLongitude();
					latitude = newArbean.getLatitude();
					break;
				}
			}
			Intent intent = new Intent();
			intent.setClass(NewARActivity.this, MapActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("type", 1);
			bundle.putDouble("longitude", longitude);
			bundle.putDouble("latitude", latitude);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
}
