package wujin.tourism.android;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	protected MyAppaplication appaplication;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appaplication = (MyAppaplication) getApplication();
		appaplication.getInstance().addActivity(this);
	}
}
