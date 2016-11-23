package wujin.tourism.android.childactivity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.adapter.FoodAdapter;
import wujin.tourism.android.childactivity.view.XListView;
import wujin.tourism.android.common.BaiduPlace;
import wujin.tourism.android.common.results;
import wujin.tourism.android.data.DialogUtils;
import wujin.tourism.android.data.GsonUtils;
import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.umeng.analytics.MobclickAgent;

public class FoodActivity extends BaseActivity
		implements android.view.View.OnClickListener, XListView.IXListViewListener {
	private TextView top_title;
	private ImageView top_back;
	private XListView xlist;
	private FoodAdapter mAdapter;
	private List<results> info = new ArrayList<results>();
	private Handler mHandler;
	private BaiduPlace baiduPlace;
	private int page_num = 1;
	private LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private double latitude;// 经度
	private double longitude;// 纬度
	private int num = 0;
	private String lat;
	private String lon;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhusutuijian);
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		mHandler = new Handler();
		initview();
	}

	private void initview() {
		top_title = (TextView) findViewById(R.id.bartitle);
		top_back = (ImageView) findViewById(R.id.barback);
		xlist = (XListView) findViewById(R.id.xlist);
		top_back.setOnClickListener(this);
		xlist.setPullLoadEnable(false);
		xlist.setXListViewListener(this);
		top_title.setText("美食推荐");
		top_back.setImageResource(R.drawable.btn_back);
		top_back.setVisibility(View.VISIBLE);
		mAdapter = new FoodAdapter(FoodActivity.this, info);
		xlist.setAdapter(mAdapter);
		if (appaplication.isOpenNetWork()) {
			xlist.mPullRefreshing = true;
			xlist.mHeaderView.setState(2);
			xlist.mHeaderView.setVisiableHeight(pxToDIP(60));
			InitLocation();
			mLocationClient.start();
		} else {
			DialogUtils.getNoInternetDg(FoodActivity.this);
		}
	}

	private int pxToDIP(int px) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
				this.getResources().getDisplayMetrics());
	}

	protected void onStop() {
		mLocationClient.stop();
		super.onStop();
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("美食推荐");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("美食推荐");
		MobclickAgent.onPause(this);
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 1000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为10000ms
		option.setIsNeedAddress(true);// 反地理编码
		mLocationClient.setLocOption(option);
	}

	private void setdata() {

		String msg = URLEncoder.encode("美食");

		FinalHttp FinalHttp = new FinalHttp();
		FinalHttp.get(
				"http://api.map.baidu.com/place/v2/search?&output=json&ak=LMmfYjRKwG3Ty4a3fGuxfasz&qq-pf-to=pcqq.c2c&scope=2&radius=5000&query="
						+ msg + "&location=" + latitude + "," + longitude + "&page_size=20&page_num=" + page_num,
				new AjaxCallBack<Object>() {
					public void onSuccess(Object t) {
						super.onSuccess(t);
						page_num = 2;
						onLoad();
						baiduPlace = (BaiduPlace) GsonUtils.jsonToObj(BaiduPlace.class, t.toString());
						if (baiduPlace.getStatus().equals("0") && !baiduPlace.getTotal().equals("0")) {
							info = (ArrayList<results>) baiduPlace.getResults();
							if (info.size() < 20) {
								xlist.mFooterView.hide();
								xlist.setPullLoadEnable(false);
							} else {
								xlist.mFooterView.show();
								xlist.setPullLoadEnable(true);
							}
							getData();
						} else {
							Toast.makeText(FoodActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
						}
					}

					public void onFailure(Throwable t, int errorNo, String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						onLoad();
						Toast.makeText(FoodActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
					}
				});

	}

	private void getData() {
		mAdapter = new FoodAdapter(FoodActivity.this, info);
		xlist.setAdapter(mAdapter);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.barback:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 实现定位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {
		public void onReceiveLocation(BDLocation location) {
			num++;
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			lat = location.getLatitude() + "";
			lon = location.getLongitude() + "";
			if (lat != null && lon != null && latitude != 4.9E-324 && longitude != 4.9E-324) {
				mLocationClient.stop();
				num = 0;
				setdata();
			} else {
				if (num == 5) {
					Toast.makeText(FoodActivity.this, "定位失败，请检查GPS和网络连接是否打开", Toast.LENGTH_LONG).show();
					mLocationClient.stop();
					onLoad();
					num = 0;
				}
			}
		}
	}

	private void onLoad() {
		xlist.mPullRefreshing = true;
		xlist.stopRefresh();
		xlist.stopLoadMore();
	}

	/**
	 * 下拉刷新：使用的Handler，进行延迟加载（postDelayed），然后从重新设置适配器
	 */
	public void onRefresh() {
		if (lat != null && lon != null && latitude != 4.9E-324 && longitude != 4.9E-324)
			setdata();
		else {
			InitLocation();
			mLocationClient.start();
		}
	}

	/**
	 * 加载更多，延迟加载postDelayed（），更新适配器
	 */
	public void onLoadMore() {

		String msg = URLEncoder.encode("美食");

		FinalHttp FinalHttp = new FinalHttp();
		FinalHttp.get(
				"http://api.map.baidu.com/place/v2/search?&output=json&ak=LMmfYjRKwG3Ty4a3fGuxfasz&qq-pf-to=pcqq.c2c&scope=2&radius=5000&query="
						+ msg + "&location=" + latitude + "," + longitude + "&page_size=20&page_num=" + page_num,
				new AjaxCallBack<Object>() {
					public void onSuccess(Object t) {
						super.onSuccess(t);
						page_num++;
						onLoad();
						baiduPlace = (BaiduPlace) GsonUtils.jsonToObj(BaiduPlace.class, t.toString());
						if (baiduPlace.getStatus().equals("0") && !baiduPlace.getTotal().equals("0")) {
							info.addAll(baiduPlace.getResults());
							if (info.size() < 20) {
								xlist.mFooterView.hide();
								xlist.setPullLoadEnable(false);
							} else {
								xlist.mFooterView.show();
								xlist.setPullLoadEnable(true);
							}
							mAdapter = new FoodAdapter(FoodActivity.this, info);
						} else {
							Toast.makeText(FoodActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
						}
					}

					public void onFailure(Throwable t, int errorNo, String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						onLoad();
						Toast.makeText(FoodActivity.this, "暂无数据", Toast.LENGTH_LONG).show();
					}
				});

	}
}
