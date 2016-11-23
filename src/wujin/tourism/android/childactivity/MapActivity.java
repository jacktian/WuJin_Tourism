package wujin.tourism.android.childactivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
//import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfigeration;
//import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOvelray;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.umeng.analytics.MobclickAgent;

//import android.R.integer;
//import android.app.Activity;
import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.EditText;
import android.widget.ImageView;
//import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import java.util.List;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;

//import android.widget.ZoomControls;
public class MapActivity extends BaseActivity implements OnClickListener, BaiduMap.OnMapClickListener,
		OnGetRoutePlanResultListener, OnGetGeoCoderResultListener {
	// private static final android.content.DialogInterface.OnClickListener
	// OnClickListener = null;
	GeoCoder mdiliSearch = null;
	private LocationClient mLocClient;
	public MyLocationListenner myListener;
	private LocationMode mCurrentMode;
	// private BitmapDescriptor mCurrentMarker;
	private boolean isFirstLoc = true;// 是否首次定位
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private PlanNode edNode;
	// 规划相关
	private RouteLine route = null;
	private OverlayManager routeOverlay = null;
	private double weidu;
	private double jindu;
	// 搜索相关
	private RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;
	private TextView m_navigationTextView;
	private String placeString = "环球动漫嬉戏谷";
	private Bundle bundle;
	private int type;
	private int lei = 1;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.maplayout);
		bundle = getIntent().getExtras();
		type = bundle.getInt("type");
		Toast.makeText(this, "正在定位...", 5000).show();
		prepareView();
		prepareviewset();
		mCurrentMode = LocationMode.NORMAL;
		myListener = new MyLocationListenner();
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15);
		mBaiduMap.setMapStatus(mapStatusUpdate);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfigeration(mCurrentMode, true, null));
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		// 地图点击事件处理
		mBaiduMap.setOnMapClickListener(this);
		mdiliSearch = GeoCoder.newInstance();
		mdiliSearch.setOnGetGeoCodeResultListener(this);
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);
	}

	/**
	 * 发起路线规划搜索示例
	 * 
	 * @param v
	 */
	public void searchButtonProcess(int i) {
		// 重置浏览节点的路线数据
		route = null;
		// mBtnPre.setVisibility(View.INVISIBLE);
		// mBtnNext.setVisibility(View.INVISIBLE);
		mBaiduMap.clear();
		// 处理搜索按钮响应
		// EditText editSt = (EditText) findViewById(R.id.start);
		// EditText editEn = (EditText) findViewById(R.id.end);
		// 设置起终点信息，对于tranist search 来说，城市名无意义
		// PlanNode stNode = PlanNode.withCityNameAndPlaceName("常州",
		// editSt.getText().toString());
		// PlanNode stNode = PlanNode.withCityNameAndPlaceName("常州", "常州火车站");
		try {
			if (type == 1) {
				LatLng latLng2 = new LatLng(bundle.getDouble("latitude"), bundle.getDouble("longitude"));
				edNode = PlanNode.withLocation(latLng2);
				dataman(i);
			}
			if (type == 2) {
				LatLng latLng3 = new LatLng(bundle.getDouble("lat"), bundle.getDouble("lng"));
				edNode = PlanNode.withLocation(latLng3);
				dataman(i);
			}
			if (type == 3) {
				lei = i;
				mdiliSearch.geocode(new GeoCodeOption().city("常州").address(bundle.getString("mapstr")));
			}
		} catch (Exception e) {
		}

	}

	private void dataman(int i) {
		LatLng latLng = new LatLng(weidu, jindu);
		PlanNode stNode = PlanNode.withLocation(latLng);
		if (i == 1) {

			mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(edNode));
		} else if (i == 2) {
			mSearch.transitSearch((new TransitRoutePlanOption()).from(stNode).city("常州").to(edNode));
		} else if (i == 3) {
			mSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(edNode));
		}
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			String aString = result.getSuggestAddrInfo().toString();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			route = result.getRouteLines().get(0);
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			// overlay.zoomToSpan();
		}
	}

	public void onGetTransitRouteResult(TransitRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			route = result.getRouteLines().get(0);
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
		}
	}

	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			// nodeIndex = -1;
			// mBtnPre.setVisibility(View.VISIBLE);
			// mBtnNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			DrivingRouteOvelray overlay = new MyDrivingRouteOverlay(mBaiduMap);
			routeOverlay = overlay;
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			// overlay.zoomToSpan();
		}
	}

	// 定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOvelray {
		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		public BitmapDescriptor getStartMarker() {
			return null;
		}

		public BitmapDescriptor getTerminalMarker() {
			return null;
		}
	}

	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {
		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		public BitmapDescriptor getStartMarker() {
			return null;
		}

		public BitmapDescriptor getTerminalMarker() {
			return null;
		}
	}

	private class MyTransitRouteOverlay extends TransitRouteOverlay {
		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		public BitmapDescriptor getStartMarker() {
			return null;
		}

		public BitmapDescriptor getTerminalMarker() {
			return null;
		}
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		m_navigationTextView = (TextView) findViewById(R.id.suretext);
		findViewById(R.id.barback).setOnClickListener(this);
		findViewById(R.id.suretext).setOnClickListener(this);
	}

	private void prepareviewset() {
		m_textviewtitle.setText("地图");
		m_navigationTextView.setText("导航");
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);
		m_navigationTextView.setVisibility(View.VISIBLE);
	}

	protected void onResume() {
		mMapView.onResume();
		super.onResume();
		MobclickAgent.onPageStart("地图");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		mMapView.onPause();
		super.onPause();
		MobclickAgent.onPageEnd("地图");
		MobclickAgent.onPause(this);
	}

	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		mdiliSearch.destroy();
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		mSearch.destroy();
		super.onDestroy();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			try {
				if (location == null || mMapView == null)
					return;
				MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
						// 此处设置开发者获取到的方向信息，顺时针0-360
						.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
				jindu = location.getLongitude();
				weidu = location.getLatitude();
				mBaiduMap.setMyLocationData(locData);
				if (isFirstLoc) {
					isFirstLoc = false;
					LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
					mBaiduMap.animateMapStatus(u);
				}
				if (type == 1) {
					searchButtonProcess(3);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.barback:
			finish();
			break;
		case R.id.suretext:
			// Toast.makeText(MapActivity.this, "经度"+jindu+"纬度"+weidu+"",
			// 1).show();
			dialog();
		default:
			break;
		}
	}

	public void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
		builder.setTitle("请选择交通方式");
		builder.setPositiveButton("驾车", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				searchButtonProcess(1);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("公交", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				searchButtonProcess(2);
				dialog.dismiss();
			}
		});
		builder.setNeutralButton("步行", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				searchButtonProcess(3);
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public void onMapClick(LatLng point) {
		mBaiduMap.hideInfoWindow();
	}

	public boolean onMapPoiClick(MapPoi poi) {
		return false;
	}

	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		LatLng latLng3 = new LatLng(result.getLocation().latitude, result.getLocation().longitude);
		edNode = PlanNode.withLocation(latLng3);
		dataman(lei);
	}

	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
			return;
		}

		Toast.makeText(MapActivity.this, result.getAddress(), Toast.LENGTH_LONG).show();

	}
}
