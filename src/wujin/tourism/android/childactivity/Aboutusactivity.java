package wujin.tourism.android.childactivity;

import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;

public class Aboutusactivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;
	private TextView versiontext;

	// private RelativeLayout linearlayoutaboutus;
	// private WebView webView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		prepareView();
		prepareviewset();
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("关于我们");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("关于我们");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		// webView = (WebView) findViewById(R.id.webview);
		// linearlayoutaboutus = (RelativeLayout)
		// findViewById(R.id.linearlayoutaboutus);
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
		versiontext = (TextView) findViewById(R.id.versiontxt);
	}

	private void prepareviewset() {
		m_textviewtitle.setText("关于我们");
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);
		try {
			versiontext.setText("当前版本：" + getVersionName());
		} catch (Exception e) {
		}
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

	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}
}
