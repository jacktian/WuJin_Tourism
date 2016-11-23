package wujin.tourism.android;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
import android.net.ConnectivityManager;
import android.util.TypedValue;
import cn.jpush.android.api.JPushInterface;
import com.umeng.analytics.MobclickAgent;

public class MyAppaplication extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	private MyAppaplication instance;

	public void onCreate() {
		super.onCreate();
		JPushInterface.setDebugMode(false);
		JPushInterface.init(this);
		instance = this;
	}

	public MyAppaplication getInstance() {
		if (null == instance)
			instance = new MyAppaplication();
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		MobclickAgent.onKillProcess(instance);
		System.exit(0);
	}

	public boolean isOpenNetWork() {
		ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	public int pxToDIP(int px) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
	}
}
