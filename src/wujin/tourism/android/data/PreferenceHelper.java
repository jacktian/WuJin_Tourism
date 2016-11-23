package wujin.tourism.android.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {
	private static SharedPreferences.Editor mEditor = null;
	private static SharedPreferences mdPreferences = null;

	private static SharedPreferences.Editor getEditor(Context paramContext) {
		if (mEditor == null)
			mEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
		return mEditor;
	}

	private static SharedPreferences getSharedPreferences(Context paramContext) {
		if (mdPreferences == null)
			mdPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
		return mdPreferences;
	}

	public static int getint(Context context, String key) {
		return PreferenceHelper.getSharedPreferences(context).getInt(key, 0);
	}

	public static void setint(Context context, int theme, String key) {
		getEditor(context).putInt(key, theme).commit();
	}
}
