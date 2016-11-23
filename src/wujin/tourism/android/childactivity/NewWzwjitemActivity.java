package wujin.tourism.android.childactivity;

import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.adapter.Wzgladapter;
import wujin.tourism.android.childactivity.view.XListView;
import wujin.tourism.android.childactivity.view.XListView.IXListViewListener;
import wujin.tourism.android.common.GsonTools;
import wujin.tourism.android.data.Datamanage;
import wujin.tourism.android.tpc.Wzglbean;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;

public class NewWzwjitemActivity extends BaseActivity implements OnClickListener, IXListViewListener {
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;
	private List<Wzglbean> wzhdarray = new ArrayList<Wzglbean>();
	private XListView listView;
	private Datamanage datamanage;
	private Wzgladapter Wzgladapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newwzgllayout);
		datamanage = new Datamanage(this);
		prepareView();
		prepareviewset();
		try {
			wzhdarray = datamanage.findAll(Wzglbean.class);
			Wzgladapter = new Wzgladapter(NewWzwjitemActivity.this, wzhdarray);
			listView.setAdapter(Wzgladapter);
		} catch (Exception e) {
		}
		onRefresh();
	}

	private void dataset() {
		FinalHttp FinalHttp1 = new FinalHttp();
		FinalHttp1.get(getResources().getString(R.string.webnetpath) + "zx/jdlist.action", new AjaxCallBack<Object>() {
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Message message1 = new Message();
				message1.obj = 1;
				wonderthandler1.sendMessage(message1);
				try {
					wzhdarray = GsonTools.readspotinformation(t.toString());
					if (wzhdarray.size() > 0) {
						Wzgladapter = new Wzgladapter(NewWzwjitemActivity.this, wzhdarray);
						listView.setAdapter(Wzgladapter);
						datamanage.deleteAll(Wzglbean.class);
						for (Wzglbean Wzglbean : wzhdarray) {
							datamanage.save(Wzglbean);
						}
					}
				} catch (Exception e) {
				}
			}

			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Message message1 = new Message();
				message1.obj = 1;
				wonderthandler1.sendMessage(message1);
				Toast.makeText(NewWzwjitemActivity.this, "获取景点数据错误", Toast.LENGTH_SHORT).show();
			}
		});
	}

	protected void stoplistview() {
		listView.mPullRefreshing = true;
		listView.stopRefresh();
		listView.stopLoadMore();
	}

	private Handler wonderthandler1 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			stoplistview();
		}
	};

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("攻略");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("攻略");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		listView = (XListView) findViewById(R.id.hdlistview);
		listView.setPullLoadEnable(false);
		listView.mFooterView.hide();
		listView.setXListViewListener(this);
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		findViewById(R.id.barback).setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				try {
					Intent intent = new Intent();
					intent.setClass(NewWzwjitemActivity.this, WzglitemActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("id", wzhdarray.get(position - 1).getId());
					bundle.putString("intro", wzhdarray.get(position - 1).getIntro());
					bundle.putString("name", wzhdarray.get(position - 1).getName());
					bundle.putString("imagelist", wzhdarray.get(position - 1).getImagelist());
					intent.putExtras(bundle);
					startActivity(intent);
				} catch (Exception e) {
				}
			}
		});
	}

	private void prepareviewset() {
		m_textviewtitle.setText("景点");
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

	public void onRefresh() {
		if (appaplication.isOpenNetWork()) {
			listView.mHeaderView.setState(2);
			listView.mHeaderView.setVisiableHeight(appaplication.pxToDIP(70));
			dataset();
		} else {
			stoplistview();
			Toast.makeText(NewWzwjitemActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
		}
	}

	public void onLoadMore() {
	}
}
