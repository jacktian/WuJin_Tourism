package wujin.tourism.android.childactivity;

import java.util.ArrayList;
import java.util.HashMap;
import net.tsz.afinal.FinalBitmap;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.adapter.WzitemlistAdapter;
import wujin.tourism.android.childFragment.pubu.PullListView;
import wujin.tourism.android.childFragment.pubu.PullListView.IXListViewListener;
import wujin.tourism.android.common.GsonTools;
import wujin.tourism.android.data.DatabaseHelper;
import wujin.tourism.android.data.HttpUtils;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;

public class WZitemActivity extends BaseActivity implements OnClickListener, IXListViewListener {
	private TextView m_textviewtitle;
	private ImageView m_imageviewback;
	private PullListView pinnedlistview;
	private String m_strid = "";
	private ArrayList<HashMap<String, Object>> listviewitems = new ArrayList<HashMap<String, Object>>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wzitemlayout);
		prepareView();
		prepareviewset();
		datareader();
		initializeAdapter();
	}

	private void datareader() {
		DatabaseHelper databasehelper = new DatabaseHelper(getApplicationContext(), 1);
		SQLiteDatabase sqlitedatabase = databasehelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			String strsql = "select * from wzinfrotable where type=" + m_strid + ";";
			cursor = sqlitedatabase.rawQuery(strsql, null);
			while (cursor.moveToNext()) {
				HashMap<String, Object> itemMap = new HashMap<String, Object>();
				itemMap.put("address", cursor.getString(cursor.getColumnIndex("address")));
				itemMap.put("description", cursor.getString(cursor.getColumnIndex("description")));
				itemMap.put("id", cursor.getString(cursor.getColumnIndex("id")));
				itemMap.put("imageUrl", cursor.getString(cursor.getColumnIndex("imageUrl")));
				itemMap.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
				itemMap.put("title", cursor.getString(cursor.getColumnIndex("title")));
				listviewitems.add(itemMap);
			}
		} catch (Exception e) {
		} finally {
			cursor.close();
			sqlitedatabase.close();
			databasehelper.close();
		}
		WzitemlistAdapter SimpleAdapter = new WzitemlistAdapter(WZitemActivity.this, listviewitems);
		pinnedlistview.setAdapter(SimpleAdapter);
	}

	private void initializeAdapter() {
		if (!appaplication.isOpenNetWork()) {
			Toast.makeText(WZitemActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
		} else {
			pinnedlistview.mPullRefreshing = true;
			pinnedlistview.mHeaderView.setVisiableHeight(pxToDIP(60));
			pinnedlistview.mHeaderView.setState(2);
			onRefresh();
		}
	}

	private int pxToDIP(int px) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
	}

	private Handler hotthandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String jsonString4 = (String) msg.obj;
			if (jsonString4.length() < 20) {
				Toast.makeText(WZitemActivity.this, "无数据", Toast.LENGTH_SHORT).show();
			} else {
				try {
					if (GsonTools.wzwujinitem(jsonString4).size() > 0) {
						listviewitems = GsonTools.wzwujinitem(jsonString4);
						FinalBitmap fb = FinalBitmap.create(WZitemActivity.this);
						// pinnedlistview.setOnScrollListener(new
						// PauseOnScrollListener(bitmapUtils, false, true));
						WzitemlistAdapter SimpleAdapter = new WzitemlistAdapter(WZitemActivity.this, listviewitems);
						pinnedlistview.setAdapter(SimpleAdapter);
						DatabaseHelper databasehelper = new DatabaseHelper(getApplicationContext(), 1);
						SQLiteDatabase sqliteDatabase = databasehelper.getWritableDatabase();
						try {
							sqliteDatabase.delete("wzinfrotable", "type=?", new String[] { m_strid });
							ContentValues contentvaluesinsert = new ContentValues();
							for (int i = 0; i < listviewitems.size(); i++) {
								contentvaluesinsert.put("address", listviewitems.get(i).get("address").toString());
								contentvaluesinsert.put("description",
										listviewitems.get(i).get("description").toString());
								contentvaluesinsert.put("id", listviewitems.get(i).get("id").toString());
								contentvaluesinsert.put("imageUrl", listviewitems.get(i).get("imageUrl").toString());
								contentvaluesinsert.put("phone", listviewitems.get(i).get("phone").toString());
								contentvaluesinsert.put("title", listviewitems.get(i).get("title").toString());
								contentvaluesinsert.put("type", m_strid);
								sqliteDatabase.insert("wzinfrotable", null, contentvaluesinsert);
							}
						} catch (Exception e) {
						} finally {
							sqliteDatabase.close();
							databasehelper.close();
						}
					} else {
						Toast.makeText(WZitemActivity.this, "无数据", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
				}
			}
			pinnedlistview.stopRefresh();
			pinnedlistview.stopLoadMore();
		}
	};

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("玩转武进详细页面");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("玩转武进详细页面");
		MobclickAgent.onPause(this);
	}

	private void prepareView() {
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_imageviewback = (ImageView) findViewById(R.id.barback);
		pinnedlistview = (PullListView) findViewById(R.id.pinnedlistview);
		pinnedlistview.mFooterView.hide();
		pinnedlistview.setPullLoadEnable(false);
		pinnedlistview.setXListViewListener(this);
		findViewById(R.id.barback).setOnClickListener(this);
	}

	private void prepareviewset() {
		try {
			Bundle bundle = this.getIntent().getExtras();
			m_strid = bundle.getString("id");
			m_textviewtitle.setText(bundle.getString("title"));
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

	public void onRefresh() {
		if (m_strid.length() > 0) {
			new Thread(new Runnable() {
				public void run() {
					try {
						String path4 = getResources().getString(R.string.webnetpath)
								+ "zx/newsjson.action?thispage=1&top=100&type=" + m_strid + "";
						String jsonString4 = HttpUtils.getJsonContent(path4);
						Message message = new Message();
						message.obj = jsonString4;
						hotthandler.sendMessage(message);
					} catch (Exception e) {
						Toast.makeText(WZitemActivity.this, "无数据", Toast.LENGTH_SHORT).show();
					} finally {
					}
				}
			}).start();
		}
	}

	public void onLoadMore() {
	}
}
