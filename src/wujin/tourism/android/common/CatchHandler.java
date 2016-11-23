package wujin.tourism.android.common;

import java.lang.Thread.UncaughtExceptionHandler;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Looper;

public class CatchHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";
	private static CatchHandler INSTANCE = new CatchHandler();
	private Context mContext;
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private CatchHandler() {
	}

	public static CatchHandler getInstance() {
		return INSTANCE;
	}

	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				new AlertDialog.Builder(mContext).setTitle("提示").setCancelable(false).setMessage("程序崩溃了...")
						.setNeutralButton("我知道了", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								System.exit(0);
							}
						}).create().show();
				Looper.loop();
			}
		}.start();
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 *
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(mContext).setTitle("提示").setMessage("程序崩溃了...").setNeutralButton("我知道了", null)
						.create().show();
			}
		});

		return true;
	}

}
