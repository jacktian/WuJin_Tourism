package wujin.tourism.android.childactivity;

import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import com.umeng.analytics.MobclickAgent;

public class PushreceiverActivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle, m_textviewcontent;
	private ImageView m_imageviewback;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pushreceiverlayout);
		prepareView();
		prepareviewset();
		try {
			Intent intent = getIntent();
			if (null != intent) {
				Bundle bundle = getIntent().getExtras();
				// String title =
				// bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
				String content = bundle.getString(JPushInterface.EXTRA_ALERT);
				m_textviewcontent.setText("    " + content);
			}
		} catch (Exception e) {
		}

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("推送页面"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("推送页面"); // 保证 onPageEnd 在onPause 之前调用,因为
											// onPause 中会保存信息
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_textviewcontent = (TextView) findViewById(R.id.pushcontent);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
	}

	private void prepareviewset() {
		m_textviewtitle.setText("推送信息");
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);
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
}
