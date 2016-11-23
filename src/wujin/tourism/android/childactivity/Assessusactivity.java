package wujin.tourism.android.childactivity;

import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;

public class Assessusactivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;
	private WebView webView;
	private RelativeLayout linearlayoutassess;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assessualayout);
		prepareView();
		prepareviewset();
		try {
			setWebView();
		} catch (Exception e) {
		}
		Toast.makeText(Assessusactivity.this, "请点击武进旅游四个字下的分数，可弹出评分对话框", Toast.LENGTH_LONG).show();
	}

	private void setWebView() {
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setSupportZoom(true);
		webView.loadUrl(
				"http://zhushou.360.cn/detail/index/soft_id/1816328?recrefer=SE_D_%E6%AD%A6%E8%BF%9B%E6%97%85%E6%B8%B8");
		webView.setWebChromeClient(new WebChromeClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("给我们评价");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("给我们评价");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		linearlayoutassess = (RelativeLayout) findViewById(R.id.linearlayoutassess);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		webView = (WebView) findViewById(R.id.webview);
		findViewById(R.id.barback).setOnClickListener(this);
	}

	private void prepareviewset() {
		m_textviewtitle.setText("给我评分");
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
