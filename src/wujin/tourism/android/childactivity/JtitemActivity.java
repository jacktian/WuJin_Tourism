package wujin.tourism.android.childactivity;

import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.customcontrol.Mywebview;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;

public class JtitemActivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;
	private String m_strid = "";
	private WebSettings webSettings;
	private Mywebview webView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jtitemlayout);
		prepareView();
		prepareviewset();
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("webviewitem");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("webviewitem");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		webView = (Mywebview) findViewById(R.id.webview);
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setSupportZoom(true);
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
	}

	private void prepareviewset() {
		try {
			Bundle bundle = this.getIntent().getExtras();
			m_strid = bundle.getString("url");
			m_textviewtitle.setText(bundle.getString("title"));
			setWebView(m_strid);
		} catch (Exception e) {
		}
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);

	}

	private void setWebView(String url) {
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
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
