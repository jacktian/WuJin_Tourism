package wujin.tourism.android.childactivity;

import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.FinalBitmap;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.adapter.Viewpageradapter;
import wujin.tourism.android.customcontrol.ChildViewPager;
import wujin.tourism.android.data.DialogUtils;
import wujin.tourism.android.data.FileHelper;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;

public class WzglitemActivity extends BaseActivity implements OnClickListener {
	private Viewpageradapter mPageAdaper;
	private TextView m_textviewtitle, textimage;
	private ImageView m_imageviewback;
	private RelativeLayout regs, repw, regw, retj, rezs, rems, rear, reqj;
	private FinalBitmap fb;
	private String id = "", titlestr = "", intro = "", imageliststr = "";
	private AnimationSet animationSet;
	private ChildViewPager m_wzwjviewpager;
	private ArrayList<View> wonderfulviews = new ArrayList<View>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wzglitemlayout);
		fb = FinalBitmap.create(this);
		fb.configLoadingImage(R.drawable.imageblod);
		fb.configLoadfailImage(R.drawable.imageblod);
		try {
			id = getIntent().getExtras().getString("id");
			titlestr = getIntent().getExtras().getString("name");
			intro = getIntent().getExtras().getString("intro");
			imageliststr = getIntent().getExtras().getString("imagelist");
		} catch (Exception e) {
		}
		m_wzwjviewpager = (ChildViewPager) findViewById(R.id.wzwj_viewpager);
		LayoutParams lp = m_wzwjviewpager.getLayoutParams();
		lp.height = getResources().getDisplayMetrics().heightPixels / 3;
		m_wzwjviewpager.setLayoutParams(lp);

		textimage = (TextView) findViewById(R.id.textimage);
		prepareView();
		prepareviewset();
		viewpagermanage();
	}

	private void viewpagermanage() {
		if (imageliststr.length() > 0) {
			final String aarr[] = imageliststr.split(",");
			for (int i = 0; i < aarr.length; i++) {
				ImageView view = new ImageView(WzglitemActivity.this);
				view.setScaleType(ScaleType.CENTER_CROP);
				wonderfulviews.add(view);
			}
			fb.display(wonderfulviews.get(0), aarr[0]);
			textimage.setText("1/" + String.valueOf(aarr.length));
			m_wzwjviewpager.setCurrentItem(0);
			mPageAdaper = new Viewpageradapter(wonderfulviews);
			m_wzwjviewpager.setAdapter(mPageAdaper);
			OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
				public void onPageSelected(int position) {
					fb.display(wonderfulviews.get(position), aarr[position]);
					textimage.setText(String.valueOf(position + 1) + "/" + String.valueOf(aarr.length));
				}

				public void onPageScrollStateChanged(int state) {
				}

				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				}
			};
			m_wzwjviewpager.setOnPageChangeListener(pageChangeListener);
		}
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(titlestr);
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(titlestr);
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.8f, 1, 0.8f, Animation.RELATIVE_TO_SELF, 0.8f,
				Animation.RELATIVE_TO_SELF, 0.8f);
		animationSet.addAnimation(scaleAnimation);
		animationSet.setDuration(20);
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		regs = (RelativeLayout) findViewById(R.id.gs);
		repw = (RelativeLayout) findViewById(R.id.piaowu);
		regw = (RelativeLayout) findViewById(R.id.gouwu);
		retj = (RelativeLayout) findViewById(R.id.tjxl);
		rezs = (RelativeLayout) findViewById(R.id.zstj);
		rems = (RelativeLayout) findViewById(R.id.mstj);
		rear = (RelativeLayout) findViewById(R.id.ar);
		reqj = (RelativeLayout) findViewById(R.id.qjty);
		findViewById(R.id.barback).setOnClickListener(this);
		findViewById(R.id.gs).setOnClickListener(this);
		findViewById(R.id.qjty).setOnClickListener(this);
		findViewById(R.id.gouwu).setOnClickListener(this);
		findViewById(R.id.piaowu).setOnClickListener(this);
		findViewById(R.id.tjxl).setOnClickListener(this);
		findViewById(R.id.zstj).setOnClickListener(this);
		findViewById(R.id.ar).setOnClickListener(this);
		findViewById(R.id.mstj).setOnClickListener(this);
	}

	private void prepareviewset() {
		m_textviewtitle.setText(titlestr);
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);
	}

	private void gaishu() {
		Intent intent = new Intent();
		intent.setClass(WzglitemActivity.this, PublicwebActivtiy.class);
		Bundle bundle = new Bundle();
		bundle.putString("title", titlestr);
		bundle.putString("url", intro);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private void armanage() {
		Intent intent = new Intent();
		intent.setClass(WzglitemActivity.this, NewARActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private void piaowu() {
		if (id.equals("29") || id.equals("31") || id.equals("32")) {
			Intent intent = new Intent();
			intent.setClass(WzglitemActivity.this, PublicwebActivtiy.class);
			Bundle bundle = new Bundle();
			bundle.putString("title", titlestr);
			switch (Integer.parseInt(id)) {
			case 29:
				bundle.putString("url",
						"http://m.ctrip.com/webapp/ticket/dest/t107405.html?from=list&name=%E6%B7%B9%E5%9F%8E%E9%87%8E%E7%94%9F%E5%8A%A8%E7%89%A9%E5%9B%AD");
				break;
			case 31:
				bundle.putString("url",
						"http://m.ctrip.com/webapp/ticket/dest/t16590.html?name=%E6%B7%B9%E5%9F%8E%E6%98%A5%E7%A7%8B%E4%B9%90%E5%9B%AD&from=search");
				break;
			case 32:
				bundle.putString("url", "http://twap.ccgogogo.com");
				break;
			default:
				break;
			}
			intent.putExtras(bundle);
			startActivity(intent);
		} else
			Toast.makeText(WzglitemActivity.this, "暂无票务信息", Toast.LENGTH_SHORT).show();

	}

	private void Qjty() {
		qjtymethod(id, "/panorama_" + id);
	}

	private void qjtymethod(String id, String urlString) {
		FileHelper filehelper = new FileHelper();
		if (filehelper.isFileDirExsit(filehelper.imagepath() + urlString)) {
			Intent qjtyIntent = new Intent(WzglitemActivity.this, QjtyActivity.class);
			qjtyIntent.putExtra("id", id);
			startActivity(qjtyIntent);
		} else {
			if (appaplication.isOpenNetWork())
				DialogUtils.showDialogquanjing(WzglitemActivity.this, "好", "取消", "请在wifi状态下下载全景包", id);
			else
				Toast.makeText(WzglitemActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.barback:
			finish();
			break;
		case R.id.gs:
			regs.startAnimation(animationSet);
			gaishu();
			break;
		case R.id.gouwu:
			regw.startAnimation(animationSet);
			if (hasGPSDevice(WzglitemActivity.this))
				openGPSshop();
			else {
				Toast.makeText(WzglitemActivity.this, "该手机无GPS设备，定位可能出现误差！", Toast.LENGTH_SHORT).show();
				Intent gwintent = new Intent(WzglitemActivity.this, ShoppingActivity.class);
				startActivity(gwintent);
			}
			break;
		case R.id.qjty:
			reqj.startAnimation(animationSet);
			Qjty();
			break;
		case R.id.piaowu:
			repw.startAnimation(animationSet);
			piaowu();
			break;
		case R.id.tjxl:
			retj.startAnimation(animationSet);
			Intent tjIntent = new Intent(WzglitemActivity.this, TjxlActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("id", id);
			tjIntent.putExtras(bundle);
			startActivity(tjIntent);
			break;
		case R.id.zstj:
			rezs.startAnimation(animationSet);
			if (hasGPSDevice(WzglitemActivity.this))
				openGPSzhusu();
			else {
				Toast.makeText(WzglitemActivity.this, "该手机无GPS设备，定位可能出现误差！", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(WzglitemActivity.this, ZhuSuTuiJianActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.ar:
			rear.startAnimation(animationSet);
			SensorManager mSM = (SensorManager) WzglitemActivity.this
					.getSystemService(WzglitemActivity.this.SENSOR_SERVICE);
			List<Sensor> sensors = mSM.getSensorList(Sensor.TYPE_ORIENTATION);
			if (sensors.size() > 0) {
				if (hasGPSDevice(WzglitemActivity.this))
					openGPSSettings();
				else
					Toast.makeText(WzglitemActivity.this, "无GPS设备，导航不可使用", Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(WzglitemActivity.this, "没有方向传感器哦，导航不可使用", Toast.LENGTH_SHORT).show();
			break;
		case R.id.mstj:
			rems.startAnimation(animationSet);
			if (hasGPSDevice(WzglitemActivity.this)) {
				openGPSFood();
			} else {
				Toast.makeText(WzglitemActivity.this, "该手机无GPS设备，定位可能出现误差！", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(WzglitemActivity.this, FoodActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	private boolean hasGPSDevice(Context context) {
		LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (mgr == null)
			return false;
		final List<String> providers = mgr.getAllProviders();
		if (providers == null)
			return false;
		return providers.contains(LocationManager.GPS_PROVIDER);
	}

	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			armanage();
		} else {
			Toast.makeText(this, "GPS未打开，请先打开GPS", Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(myIntent);
		}
	}

	private void openGPSzhusu() {
		LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Intent intent = new Intent(WzglitemActivity.this, ZhuSuTuiJianActivity.class);
			startActivity(intent);
		} else
			DialogUtils.showDialogzhusu(WzglitemActivity.this, "好", "拒绝", "为提高定位成功率和准确率，强烈建议打开GPS");
	}

	private void openGPSFood() {
		LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Intent intent = new Intent(WzglitemActivity.this, FoodActivity.class);
			startActivity(intent);
		} else
			DialogUtils.showDialogFood(WzglitemActivity.this, "好", "拒绝", "为提高定位成功率和准确率，强烈建议打开GPS");
	}

	private void openGPSshop() {
		LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Intent intent = new Intent(WzglitemActivity.this, ShoppingActivity.class);
			startActivity(intent);
		} else
			DialogUtils.showDialogshop(WzglitemActivity.this, "好", "拒绝", "为提高定位成功率和准确率，强烈建议打开GPS");
	}
}
