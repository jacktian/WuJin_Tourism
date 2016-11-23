package wujin.tourism.android.childactivity;

import net.tsz.afinal.FinalBitmap;
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

public class WzitemdetailActivity extends BaseActivity implements OnClickListener {
	private TextView m_textviewtitle, detailtitle, detailphone, detailaddress, description;
	private ImageView m_imageviewback, detailimage;
	private FinalBitmap fb;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wzitemdetail);
		fb = FinalBitmap.create(this);
		fb.configLoadingImage(R.drawable.imageblod);
		fb.configLoadfailImage(R.drawable.imageblod);
		prepareView();
		prepareviewset();
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("wzitem详情");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("wzitem详情");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		detailtitle = (TextView) findViewById(R.id.detailtitle);
		detailphone = (TextView) findViewById(R.id.detailphone);
		detailaddress = (TextView) findViewById(R.id.detailaddress);
		description = (TextView) findViewById(R.id.description);
		detailimage = (ImageView) findViewById(R.id.detailimage);
		findViewById(R.id.detailtitle).setOnClickListener(this);
		findViewById(R.id.detailphone).setOnClickListener(this);
		findViewById(R.id.detailaddress).setOnClickListener(this);
		findViewById(R.id.description).setOnClickListener(this);
		findViewById(R.id.barback).setOnClickListener(this);
		try {
			Bundle bundle = this.getIntent().getExtras();
			m_textviewtitle.setText(bundle.getString("title"));
			detailtitle.setText(bundle.getString("title"));
			if (bundle.getString("imageUrl").length() > 10) {
				fb.display(detailimage, bundle.getString("imageUrl"));
			} else
				detailimage.setVisibility(View.GONE);
			if (bundle.getString("phone").length() > 2)
				detailphone.setText("电话：" + bundle.getString("phone"));
			else
				detailphone.setText("电话：暂无");
			if (bundle.getString("address").length() > 3)
				detailaddress.setText("地址：" + bundle.getString("address"));
			else
				detailaddress.setText("地址：暂无");
			description.setText(bundle.getString("description"));
		} catch (Exception e) {
		}
	}

	private void prepareviewset() {
		m_imageviewback.setImageResource(R.drawable.btn_back);
		m_imageviewback.setVisibility(View.VISIBLE);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.barback:
			finish();
			break;
		case R.id.detailphone:
			try {
				if (this.getIntent().getExtras().getString("phone").length() > 2) {
					Intent intent = new Intent(Intent.ACTION_CALL,
							Uri.parse("tel:" + this.getIntent().getExtras().getString("phone")));
					startActivity(intent);
				}
			} catch (Exception e) {
			}
			break;
		case R.id.detailaddress:
			try {
				if (this.getIntent().getExtras().getString("address").length() > 4) {
					Intent intent = new Intent();
					intent.setClass(WzitemdetailActivity.this, MapActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("type", 3);
					bundle.putString("mapstr", this.getIntent().getExtras().getString("address"));
					intent.putExtras(bundle);
					startActivity(intent);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		default:
			break;
		}
	}
}
