package wujin.tourism.android.childactivity;

import java.io.File;
import wujin.tourism.android.BaseActivity;
import wujin.tourism.android.R;
import wujin.tourism.android.alertorpop.BasePop;
import wujin.tourism.android.common.MethodsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

public class SetActivity extends BaseActivity implements OnClickListener {
	private BasePop popView;
	private View m_viewpop;
	private LayoutInflater m_inflater;
	private TextView m_textviewtitle;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setfragment);
		m_textviewtitle = (TextView) findViewById(R.id.bartitle);
		m_textviewtitle.setText("设置");
		MobclickAgent.updateOnlineConfig(this);
		findViewById(R.id.qkhc).setOnClickListener(this);
		findViewById(R.id.tjyy).setOnClickListener(this);
		findViewById(R.id.gywm).setOnClickListener(this);
		findViewById(R.id.jcgx).setOnClickListener(this);
		findViewById(R.id.gwpf).setOnClickListener(this);
		findViewById(R.id.syfk).setOnClickListener(this);
		findViewById(R.id.cydh).setOnClickListener(this);
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("设置页面");
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("设置页面");
		MobclickAgent.onPause(this);
	}

	private void popviewclearcachelayout() {
		if (m_inflater == null) {
			m_inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}
		m_viewpop = m_inflater.inflate(R.layout.lookmodepop, null);
		Button clearcachesure = (Button) m_viewpop.findViewById(R.id.daymode);
		clearcachesure.setText("清空缓存");
		Button cancel = (Button) m_viewpop.findViewById(R.id.nightmode);
		cancel.setText("取消");
		TextView laytextview = (TextView) m_viewpop.findViewById(R.id.laytext);
		laytextview.setText("确定清空缓存？");
		popView = new BasePop(this, m_viewpop, 0);
		popView.showAtLocation(findViewById(R.id.setlayout), Gravity.CENTER, 0, 0);
		clearcachesure.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				File file = null;
				if (file != null && file.exists() && file.isDirectory()) {
					for (File item : file.listFiles()) {
						item.delete();
					}
					file.delete();
				}
				clearCacheFolder(getFilesDir(), System.currentTimeMillis());
				clearCacheFolder(getCacheDir(), System.currentTimeMillis());
				if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
					clearCacheFolder(MethodsCompat.getExternalCacheDir(SetActivity.this), System.currentTimeMillis());
				}
				Toast.makeText(SetActivity.this, "清理成功", Toast.LENGTH_SHORT).show();
				popView.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popView.dismiss();
			}
		});
	}

	public boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	private int clearCacheFolder(File dir, long curTime) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, curTime);
					}
					if (child.lastModified() < curTime) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.qkhc:
			popviewclearcachelayout();
			break;
		case R.id.tjyy:
			Intent intentaboutus = new Intent();
			intentaboutus.setClass(this, RecomandActivity.class);
			startActivity(intentaboutus);
			break;
		case R.id.gywm:
			Intent intentgywm = new Intent();
			intentgywm.setClass(this, Aboutusactivity.class);
			startActivity(intentgywm);
			break;
		case R.id.jcgx:
			UmengUpdateAgent.setUpdateOnlyWifi(false);
			UmengUpdateAgent.setUpdateAutoPopup(false);
			UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
				public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
					switch (updateStatus) {
					case UpdateStatus.Yes:
						UmengUpdateAgent.showUpdateDialog(SetActivity.this, updateInfo);
						break;
					case UpdateStatus.No:
						Toast.makeText(SetActivity.this, "已经是最新版本", Toast.LENGTH_SHORT).show();
						break;
					case UpdateStatus.NoneWifi:
						Toast.makeText(SetActivity.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
						break;
					case UpdateStatus.Timeout:
						Toast.makeText(SetActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
						break;
					}
				}
			});
			UmengUpdateAgent.update(this);
			break;
		case R.id.gwpf:
			Intent intentgwpf = new Intent();
			intentgwpf.setClass(this, Assessusactivity.class);
			startActivity(intentgwpf);
			break;
		case R.id.syfk:
			FeedbackAgent agent = new FeedbackAgent(this);
			agent.startFeedbackActivity();
			break;
		case R.id.cydh:
			Intent intentcydh = new Intent();
			intentcydh.setClass(this, NormalTelActivity.class);
			startActivity(intentcydh);
			break;
		default:
			break;
		}
	}
}
