package wujin.tourism.android.childactivity;

import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;

public class NormalTelActivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.normaltellayout);
		prepareView();
		prepareviewset();
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("常用电话");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("常用电话");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
		findViewById(R.id.fei).setOnClickListener(this);
		findViewById(R.id.fire).setOnClickListener(this);
		findViewById(R.id.jiuzhu).setOnClickListener(this);
	}

	private void prepareviewset() {
		m_textviewtitle.setText("常用电话");
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.barback:
			finish();
			break;
		case R.id.fire:
			Intent intentfire = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "119"));
			startActivity(intentfire);
			break;
		case R.id.fei:
			Intent intentfei = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "110"));
			startActivity(intentfei);
			break;
		case R.id.jiuzhu:
			Intent intentjiuzhu = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "120"));
			startActivity(intentjiuzhu);
			break;
		default:
			break;
		}
	}
}
