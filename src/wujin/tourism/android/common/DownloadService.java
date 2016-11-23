package wujin.tourism.android.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.Toast;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;
import wujin.tourism.android.R;
import wujin.tourism.android.data.FileHelper;

public class DownloadService extends Service {
	private static final int NOTIFY_ID = 0;
	private Context mContext = this;
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	int rate = 0;

	public void onCreate() {
		super.onCreate();
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		String loadurl = intent.getStringExtra("url");
		final String pathString = intent.getStringExtra("path");
		final String zippathString = intent.getStringExtra("zippath");
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "开始下载";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.layou);
		contentView.setTextViewText(R.id.tv_rv, "正在下载");
		mNotification.contentView = contentView;
		Intent intnt = new Intent(this, Service.class);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intnt, PendingIntent.FLAG_UPDATE_CURRENT);
		mNotification.contentIntent = contentIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
		FileHelper filehelper = new FileHelper();
		filehelper.IsSDExistandcreatedir();

		try {
			if (filehelper.isFileExist(pathString))
				filehelper.delSDFile(pathString);
		} catch (Exception e) {
		}
		FinalHttp fh = new FinalHttp();
		@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
		HttpHandler handler = fh.download(loadurl, filehelper.imagepath() + pathString, new AjaxCallBack() {
			public void onLoading(long count, long current) {
				DecimalFormat df = new DecimalFormat("#####0.00");
				String aaS = df.format(((double) current / (double) count));
				float rate1 = (Float.parseFloat(aaS)) * 100;
				rate = (int) rate1;
				RemoteViews contentView = mNotification.contentView;
				contentView.setTextViewText(R.id.tv_rv, rate + "%");
				contentView.setProgressBar(R.id.pb_rv, 100, rate, false);
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				Intent intent = new Intent();
				intent.setAction("android.intent.action.MY_RECEIVER");
				intent.putExtra("progress", rate);
				sendBroadcast(intent);
				if (current == count) {
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotification.contentView = null;
					mNotificationManager.cancel(NOTIFY_ID);
					stopSelf();
					Toast.makeText(mContext, "正在解压", Toast.LENGTH_SHORT).show();
					new Thread(new Runnable() {
						public void run() {
							Looper.prepare();
							FileHelper filehelper = new FileHelper();
							try {
								filehelper.IsfileExistandcreatedir(filehelper.SDPATH() + "/wujinly" + zippathString);
								ZIP.UnZipFolder(filehelper.SDPATH() + "/wujinly" + pathString,
										filehelper.SDPATH() + "/wujinly" + zippathString);
								Toast.makeText(mContext, "解压完成", Toast.LENGTH_SHORT).show();
							} catch (Exception e) {
								Toast.makeText(mContext, "解压错误", Toast.LENGTH_SHORT).show();
							}
							Looper.loop();
						}
					}).start();
				}
			}

			public void onSuccess(File t) {
				super.onSuccess(t);
			}

			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Toast.makeText(mContext, "下载失败，请稍后再试", Toast.LENGTH_SHORT).show();
				mNotification.flags = Notification.FLAG_AUTO_CANCEL;
				mNotification.contentView = null;
				mNotificationManager.cancel(NOTIFY_ID);
			}
		});
	}

	public int upZipFile(File zipFile, String folderPath) throws ZipException, IOException {
		ZipFile zfile = new ZipFile(zipFile);
		Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				String dirstr = folderPath + ze.getName();
				dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
				File f = new File(dirstr);
				f.mkdir();
				continue;
			}
			OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
		}
		zfile.close();
		return 0;
	}

	public static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		String substr = null;
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				substr = dirs[i];
				try {
					substr = new String(substr.getBytes("8859_1"), "GB2312");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				ret = new File(ret, substr);
			}
			if (!ret.exists())
				ret.mkdirs();
			substr = dirs[dirs.length - 1];
			try {
				substr = new String(substr.getBytes("8859_1"), "GB2312");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ret = new File(ret, substr);
			return ret;
		}
		return ret;
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public IBinder onBind(Intent intent) {
		return null;
	}
}