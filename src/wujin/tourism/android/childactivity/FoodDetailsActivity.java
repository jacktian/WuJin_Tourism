package wujin.tourism.android.childactivity;

import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.common.results;
import wujin.tourism.android.customcontrol.Mywebview;
import wujin.tourism.android.data.DialogUtils;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;

public class FoodDetailsActivity extends BaseActivity implements OnClickListener {
	private results info;
	private Mywebview webView;
	private WebSettings webSettings;
	private ImageView mbuttonback, mbuttonForward, mbuttonreload;
	private TextView top_title;
	private ImageView top_back;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhusudetail);
		try {
			info = (results) getIntent().getSerializableExtra("DATA");
		} catch (Exception e) {
		}
		webView = (Mywebview) findViewById(R.id.webview);
		mbuttonback = (ImageView) findViewById(R.id.webviewback);
		mbuttonForward = (ImageView) findViewById(R.id.webviewforword);
		mbuttonreload = (ImageView) findViewById(R.id.webviewreload);
		top_title = (TextView) findViewById(R.id.bartitle);
		top_back = (ImageView) findViewById(R.id.barback);
		top_back.setOnClickListener(this);
		mbuttonback.setOnClickListener(this);
		mbuttonForward.setOnClickListener(this);
		mbuttonreload.setOnClickListener(this);
		top_title.setText("美食推荐详情");
		top_back.setImageResource(R.drawable.btn_back);
		top_back.setVisibility(View.VISIBLE);
		Setview();
		if (appaplication.isOpenNetWork()) {
			webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
			if (info.getDetail_info().getDetail_url() != null) {
				setWebView(info.getDetail_info().getDetail_url());
			}
		} else {
			DialogUtils.getNoInternetDg(FoodDetailsActivity.this);
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void Setview() {
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setSupportZoom(true);
	}

	private void setWebView(String url) {
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@SuppressWarnings("deprecation")
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				view.stopLoading();
				view.clearView();
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.webviewback:
			if (webView.canGoBack())
				webView.goBack();
			break;
		case R.id.webviewforword:
			if (webView.canGoForward())
				webView.goForward();
			break;
		case R.id.webviewreload:
			webView.reload();
			break;
		case R.id.barback:
			finish();
			break;
		default:
			break;
		}
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("美食推荐详情");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("美食推荐详情");
		MobclickAgent.onPause(this);
	}
}
