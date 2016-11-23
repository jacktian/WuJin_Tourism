package wujin.tourism.android.childactivity;

import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;

public class JtActivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jtlayout);
		prepareView();
		prepareviewset();
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("交通");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("交通");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
		findViewById(R.id.hklx).setOnClickListener(this);
		findViewById(R.id.tllx).setOnClickListener(this);
		findViewById(R.id.ctqc).setOnClickListener(this);
		findViewById(R.id.brt).setOnClickListener(this);
		findViewById(R.id.zj).setOnClickListener(this);
		findViewById(R.id.ohter).setOnClickListener(this);
	}

	private void prepareviewset() {
		m_textviewtitle.setText("交通");
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.barback) {
			finish();
			return;
		}
		Intent intent = new Intent();
		intent.setClass(JtActivity.this, JtitemActivity.class);
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.hklx:
			bundle.putString("title", "交通-航空");
			bundle.putString("url", "file:///android_asset/hkly.htm");
			break;
		case R.id.tllx:
			bundle.putString("title", "交通-铁路");
			bundle.putString("url", "file:///android_asset/tllx.htm");
			break;
		case R.id.ctqc:
			bundle.putString("title", "交通-长途汽车");
			bundle.putString("url", "file:///android_asset/ctqc.htm");
			break;
		case R.id.brt:
			bundle.putString("title", "交通-BRT汽车");
			bundle.putString("url", "file:///android_asset/brt.htm");
			break;
		case R.id.zj:
			bundle.putString("title", "交通-自驾");
			bundle.putString("url", "file:///android_asset/zj.htm");
			break;
		case R.id.ohter:
			bundle.putString("title", "交通-其他交通");
			bundle.putString("url", "file:///android_asset/ohter.htm");
			break;
		default:
			break;
		}
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
