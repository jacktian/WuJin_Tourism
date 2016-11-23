package wujin.tourism.android.childactivity;

import net.tsz.afinal.FinalBitmap;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class WZviewpagerActivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle, wzitemlistviewaddress, wzitemlistviewtel, wzitemlistviewdes;
	private ImageView m_imageviewback;
	private FinalBitmap fb;
	private ImageView wzitemlistviewimage;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wzvplayout);
		fb = FinalBitmap.create(this);
		fb.configLoadingImage(R.drawable.imageblod);
		fb.configLoadfailImage(R.drawable.imageblod);
		prepareView();
		prepareviewset();
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("玩转武进滑动页");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("玩转武进滑动页");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_textviewtitle.setSingleLine(true);
		m_textviewtitle.setWidth(getResources().getDisplayMetrics().widthPixels / 3 * 2);
		m_textviewtitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		m_textviewtitle.setFocusable(true);
		m_textviewtitle.setFocusableInTouchMode(true);
		m_textviewtitle.setSingleLine(true);
		m_textviewtitle.setMarqueeRepeatLimit(-1);

		wzitemlistviewaddress = (TextView) findViewById(R.id.wzitemlistviewaddress);
		wzitemlistviewtel = (TextView) findViewById(R.id.wzitemlistviewtel);
		wzitemlistviewimage = (ImageView) findViewById(R.id.wzitemlistviewimage);
		wzitemlistviewimage.setScaleType(ScaleType.CENTER_CROP);
		wzitemlistviewdes = (TextView) findViewById(R.id.wzitemlistviewdes);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
	}

	private void prepareviewset() {
		try {
			Bundle bundle = this.getIntent().getExtras();
			m_textviewtitle.setText(bundle.getString("title"));
			wzitemlistviewaddress.setText(bundle.getString("address"));
			wzitemlistviewtel.setText(bundle.getString("phone"));
			CharSequence charSequence = Html.fromHtml(bundle.getString("description"));
			wzitemlistviewdes.setText(charSequence);
			wzitemlistviewdes.setMovementMethod(LinkMovementMethod.getInstance());
			fb.display(wzitemlistviewimage, bundle.getString("imageUrl"));
		} catch (Exception e) {
		}

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
