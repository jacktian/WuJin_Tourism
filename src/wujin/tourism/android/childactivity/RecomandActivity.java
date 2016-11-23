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
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;

public class RecomandActivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommendapp);
		prepareView();
		prepareviewset();
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
		findViewById(R.id.xxg).setOnClickListener(this);
		findViewById(R.id.thw).setOnClickListener(this);
	}

	private void prepareviewset() {
		m_textviewtitle.setText("推荐应用");
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.barback:
			finish();
			break;
		case R.id.xxg:
			if (appaplication.isOpenNetWork()) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://218.93.39.237:8082/xxgapp/IntInit.apk");
				intent.setData(content_url);
				startActivity(intent);

			} else
				Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
			break;
		case R.id.thw:
			if (appaplication.isOpenNetWork()) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://218.93.39.237:8082/thwapp/thw.apk");
				intent.setData(content_url);
				startActivity(intent);
			} else
				Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("推荐应用");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("推荐应用");
		MobclickAgent.onPause(this);
	}
}