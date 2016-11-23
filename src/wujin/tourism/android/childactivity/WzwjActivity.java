package wujin.tourism.android.childactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.tsz.afinal.FinalBitmap;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.adapter.MaingridViewadapter;
import wujin.tourism.android.adapter.Mainlistadapter;
import wujin.tourism.android.adapter.Viewpageradapter;
import wujin.tourism.android.common.GsonTools;
import wujin.tourism.android.common.mainnewsbean;
import wujin.tourism.android.customcontrol.ChildViewPager;
import wujin.tourism.android.customcontrol.MyGirdView;
import wujin.tourism.android.customcontrol.Mylistview;
import wujin.tourism.android.data.DatabaseHelper;
import wujin.tourism.android.data.Datamanage;
import wujin.tourism.android.data.HttpUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

public class WzwjActivity extends BaseActivity {
	private Viewpageradapter mPageAdaper;
	private ChildViewPager m_wzwjviewpager;
	private LinearLayout m_LinearLayoutimage;
	private TextView m_wonderfultextview1, titleView;
	private FinalBitmap fb;
	private ArrayList<HashMap<String, Object>> listviewitems = new ArrayList<HashMap<String, Object>>();
	private ArrayList<View> wonderfulviews = new ArrayList<View>();
	private AnimationSet animationSet;
	private long firstTime = 0;
	private ImageView m_ImageViewRight;
	private MyGirdView m_mainGridView;
	private Mylistview m_mainListView;
	private List<mainnewsbean> arrayList = new ArrayList<mainnewsbean>();
	private Datamanage datamanage;
	private Mainlistadapter newsadapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wzfragment);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		titleView = (TextView) findViewById(R.id.bartitle);
		m_mainGridView = (MyGirdView) findViewById(R.id.maingridview);
		m_mainGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
				try {
					if (position == 0) {
						Intent intent = new Intent();
						intent.setClass(WzwjActivity.this, NewWzwjitemActivity.class);
						startActivity(intent);
						return;
					} else if (position == 6) {
						Intent intent = new Intent();
						intent.setClass(WzwjActivity.this, JtActivity.class);
						startActivity(intent);
						return;
					} else if (position == 7) {
						Dialog dialog = new AlertDialog.Builder(WzwjActivity.this).setTitle("提示").setMessage("敬请期待")
								.setPositiveButton("ok", new android.content.DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								}).create();
						dialog.show();
						return;
					} else {
						Intent intent = new Intent();
						intent.setClass(WzwjActivity.this, WZitemActivity.class);
						Bundle bundle = new Bundle();
						switch (position) {
						case 1:
							bundle.putString("title", "美食");
							bundle.putString("id", "2");
							break;
						case 2:
							bundle.putString("title", "酒店");
							bundle.putString("id", "3");
							break;
						case 3:
							bundle.putString("title", "娱乐");
							bundle.putString("id", "6");
							break;
						case 4:
							bundle.putString("title", "特产");
							bundle.putString("id", "5");
							break;
						case 5:
							bundle.putString("title", "购物");
							bundle.putString("id", "4");
							break;
						default:
							break;
						}
						intent.putExtras(bundle);
						startActivity(intent);
					}
				} catch (Exception e) {
				}
			}
		});
		m_mainListView = (Mylistview) findViewById(R.id.mainlsitview);
		MaingridViewadapter mainadapter = new MaingridViewadapter(this);
		m_mainGridView.setAdapter(mainadapter);
		titleView.setText("玩转武进");
		m_ImageViewRight = (ImageView) findViewById(R.id.shortcut);
		m_ImageViewRight.setVisibility(View.VISIBLE);
		m_ImageViewRight.setImageResource(R.drawable.btn_set);
		m_ImageViewRight.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(WzwjActivity.this, SetActivity.class);
				startActivity(intent);
			}
		});
		fb = FinalBitmap.create(this);
		fb.configLoadingImage(R.drawable.imageblod);
		fb.configLoadfailImage(R.drawable.imageblod);
		m_wonderfultextview1 = (TextView) findViewById(R.id.viewpage1);
		m_wzwjviewpager = (ChildViewPager) findViewById(R.id.wzwj_viewpager);
		LayoutParams lp = m_wzwjviewpager.getLayoutParams();
		lp.height = getResources().getDisplayMetrics().heightPixels / 3;
		m_wzwjviewpager.setLayoutParams(lp);
		m_LinearLayoutimage = (LinearLayout) findViewById(R.id.linearlayoutimage);
		SetView();
		datamanage = new Datamanage(this);
		try {
			arrayList = datamanage.findAll(mainnewsbean.class);
			newsadapter = new Mainlistadapter(this, arrayList);
			m_mainListView.setAdapter(newsadapter);
		} catch (Exception e) {
		}
		if (appaplication.isOpenNetWork()) {
			com.lidroid.xutils.HttpUtils http1 = new com.lidroid.xutils.HttpUtils();
			http1.send(HttpRequest.HttpMethod.GET,
					getResources().getString(R.string.webnetpath) + "zx/newsjson.action?thispage=1&top=50&type=8",
					new RequestCallBack<String>() {
						public void onLoading(long total, long current, boolean isUploading) {
						}

						public void onSuccess(ResponseInfo<String> responseInfo) {
							List<mainnewsbean> testlist = new ArrayList<mainnewsbean>();
							try {
								testlist = GsonTools.retrunmainnewlist(responseInfo.result);
								if (testlist.size() > 0) {
									newsadapter = new Mainlistadapter(WzwjActivity.this, testlist);
									m_mainListView.setAdapter(newsadapter);
									try {
										datamanage.deleteAll(mainnewsbean.class);
										for (mainnewsbean mainnewsbean : testlist) {
											datamanage.save(mainnewsbean);
										}
									} catch (Exception e) {
									}
								}
							} catch (Exception e) {
							}
						}

						public void onStart() {
						}

						public void onFailure(HttpException error, String msg) {
							Toast.makeText(WzwjActivity.this, "获取列表失败", Toast.LENGTH_SHORT).show();
						}
					});
		}
	}

	private void SetView() {
		animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.8f, 1, 0.8f, Animation.RELATIVE_TO_SELF, 0.8f,
				Animation.RELATIVE_TO_SELF, 0.8f);
		animationSet.addAnimation(scaleAnimation);
		animationSet.setDuration(20);
		DatabaseHelper databasehelper = new DatabaseHelper(WzwjActivity.this, 1);
		SQLiteDatabase sqlitedatabase = databasehelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			String strsql = "select * from wzinfrotable where type=7;";
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
		if (listviewitems.size() > 0)
			viewpagermanage();
		if (!appaplication.isOpenNetWork()) {
		} else {
			new Thread(new Runnable() {
				public void run() {
					try {
						String path4 = getResources().getString(R.string.webnetpath)
								+ "zx/newsjson.action?thispage=1&top=100&type=7";
						String jsonString4 = HttpUtils.getJsonContent(path4);
						Message message = new Message();
						message.obj = jsonString4;
						hotthandler.sendMessage(message);
					} catch (Exception e) {
					} finally {
					}
				}
			}).start();
		}
	}

	private Handler hotthandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String jsonString4 = (String) msg.obj;
			if (jsonString4.length() < 20) {
			} else {
				try {
					if (GsonTools.wzwujinitem(jsonString4).size() > 0)
						listviewitems = GsonTools.wzwujinitem(jsonString4);
				} catch (Exception e) {
				}
				if (listviewitems.size() > 0) {
					viewpagermanage();
					DatabaseHelper databasehelper = new DatabaseHelper(WzwjActivity.this, 1);
					SQLiteDatabase sqliteDatabase = databasehelper.getWritableDatabase();
					try {
						sqliteDatabase.delete("wzinfrotable", "type=?", new String[] { "7" });
						ContentValues contentvaluesinsert = new ContentValues();
						for (int i = 0; i < listviewitems.size(); i++) {
							contentvaluesinsert.put("address", listviewitems.get(i).get("address").toString());
							contentvaluesinsert.put("description", listviewitems.get(i).get("description").toString());
							contentvaluesinsert.put("id", listviewitems.get(i).get("id").toString());
							contentvaluesinsert.put("imageUrl", listviewitems.get(i).get("imageUrl").toString());
							contentvaluesinsert.put("phone", listviewitems.get(i).get("phone").toString());
							contentvaluesinsert.put("title", listviewitems.get(i).get("title").toString());
							contentvaluesinsert.put("type", "7");
							sqliteDatabase.insert("wzinfrotable", null, contentvaluesinsert);
						}
					} catch (Exception e) {
					} finally {
						sqliteDatabase.close();
						databasehelper.close();
					}
				}
			}
		}
	};

	private void viewpagermanage() {
		m_wzwjviewpager.removeAllViews();
		wonderfulviews.clear();
		m_LinearLayoutimage.removeAllViews();
		for (int i = 0; i < listviewitems.size(); i++) {
			ImageView view = new ImageView(WzwjActivity.this);
			view.setScaleType(ScaleType.CENTER_CROP);
			wonderfulviews.add(view);
		}
		for (int i = 0; i < listviewitems.size(); i++) {
			ImageView icon = new ImageView(WzwjActivity.this);
			LinearLayout.LayoutParams layoutparamsImageViewPrompt = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutparamsImageViewPrompt.weight = 1;
			m_LinearLayoutimage.addView(icon, layoutparamsImageViewPrompt);
		}
		fb.display(wonderfulviews.get(0), listviewitems.get(0).get("imageUrl").toString());
		m_wonderfultextview1.setText(listviewitems.get(0).get("title").toString());
		for (int i = 0; i < listviewitems.size(); i++) {
			ImageView view = (ImageView) m_LinearLayoutimage.getChildAt(i);
			view.setImageResource(R.drawable.page_now);
		}
		for (int i = 0; i < listviewitems.size(); i++) {
			final HashMap<String, Object> map = listviewitems.get(i);
			wonderfulviews.get(i).setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					Intent intent = new Intent();
					intent.setClass(WzwjActivity.this, WZviewpagerActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("title", map.get("title").toString());
					bundle.putString("address", map.get("address").toString());
					bundle.putString("description", map.get("description").toString());
					bundle.putString("imageUrl", map.get("imageUrl").toString());
					bundle.putString("phone", map.get("phone").toString());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}
		ImageView view1 = (ImageView) m_LinearLayoutimage.getChildAt(0);
		view1.setImageResource(R.drawable.page);
		m_wzwjviewpager.setCurrentItem(0);
		mPageAdaper = new Viewpageradapter(wonderfulviews);
		m_wzwjviewpager.setAdapter(mPageAdaper);
		OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
			public void onPageSelected(int position) {
				m_wonderfultextview1.setText(listviewitems.get(position).get("title").toString());
				fb.display(wonderfulviews.get(position), listviewitems.get(position).get("imageUrl").toString());
				for (int i = 0; i < listviewitems.size(); i++) {
					ImageView view = (ImageView) m_LinearLayoutimage.getChildAt(i);
					view.setImageResource(R.drawable.page_now);
				}
				ImageView view1 = (ImageView) m_LinearLayoutimage.getChildAt(position);
				view1.setImageResource(R.drawable.page);
			}

			public void onPageScrollStateChanged(int state) {
			}

			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}
		};
		m_wzwjviewpager.setOnPageChangeListener(pageChangeListener);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("玩转武进");
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("玩转武进");
		MobclickAgent.onPause(this);
	}

	public boolean onKeyDown(int keyCoder, KeyEvent event) {
		if (keyCoder == KeyEvent.KEYCODE_BACK) {
			exitsystem();
		}
		return false;
	}

	private void exitsystem() {
		long secondTime = System.currentTimeMillis();
		if (secondTime - firstTime > 2000) {
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			firstTime = secondTime;
		} else {
			appaplication.exit();
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		appaplication.exit();
	}
}
