package wujin.tourism.android;

import wujin.tourism.android.childactivity.WzwjActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends BaseActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final View view = View.inflate(this, R.layout.welcome, null);
		setContentView(view);
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation arg0) {
				Intent intent = new Intent();
				intent.setClass(WelcomeActivity.this, WzwjActivity.class);
				startActivity(intent);
				finish();
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationStart(Animation animation) {
			}
		});
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("欢迎页面"); // 统计页面
		MobclickAgent.onResume(this); // 统计时长
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("欢迎页面"); // 保证 onPageEnd 在onPause 之前调用,因为
											// onPause 中会保存信息
		MobclickAgent.onPause(this);
	}
	// public void onDestroy() {
	// super.onDestroy();
	// android.os.Debug.stopMethodTracing();
	// }
}
