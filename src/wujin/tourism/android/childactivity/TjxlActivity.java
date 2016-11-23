package wujin.tourism.android.childactivity;

import java.util.ArrayList;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.adapter.Tjxladapter;
import wujin.tourism.android.tpc.Timestreambean;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;

public class TjxlActivity extends BaseActivity implements OnClickListener {
	private ListView m_listViewdemo;
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;
	private ArrayList<Timestreambean> wzglarray = new ArrayList<Timestreambean>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tjxllayout);
		prepareView();
		prepareviewset();
		m_listViewdemo = (ListView) findViewById(R.id.listView1);

		Tjxladapter SimpleAdapter = new Tjxladapter(this, wzglarray);
		m_listViewdemo.setAdapter(SimpleAdapter);
	}

	private Timestreambean timestreambeanitem(String text, int omage, String time) {
		Timestreambean wzglbean = new Timestreambean();
		wzglbean.setTextcontent(text);
		wzglbean.setTime(time);
		wzglbean.setImage(omage);
		return wzglbean;
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("推荐线路");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("推荐线路");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
	}

	private void prepareviewset() {
		m_textviewtitle.setText("推荐线路");
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
