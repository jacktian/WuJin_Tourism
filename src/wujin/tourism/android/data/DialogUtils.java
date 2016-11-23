package wujin.tourism.android.data;

import wujin.tourism.android.R;
import wujin.tourism.android.childactivity.FoodActivity;
import wujin.tourism.android.childactivity.ShoppingActivity;
import wujin.tourism.android.childactivity.WzglitemActivity;
import wujin.tourism.android.childactivity.ZhuSuTuiJianActivity;
import wujin.tourism.android.common.DownloadService;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DialogUtils {
	public static ProgressDialog dialog;
	public static Dialog dialog1;

	public static void startProgressDialog(Context context) {
		// DismissProgressDialog();
		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("请稍候...");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);// 点击空白区域不会消失
		/*
		 * dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
		 * 
		 * @Override public void onCancel(DialogInterface dialog) { // TODO
		 * Auto-generated method stub } });
		 */
		dialog.show();
	}

	public static void startProgressDialog(Context context, String message) {
		DismissProgressDialog();
		dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage(message);
		dialog.setCancelable(true);
		/*
		 * dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
		 * 
		 * @Override public void onCancel(DialogInterface dialog) { // TODO
		 * Auto-generated method stub } });
		 */
		dialog.show();
	}

	/**
	 * dialog dismiss
	 */
	public static void DismissProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public static void DismissProgressDialog1() {
		if (dialog1 != null && dialog1.isShowing()) {
			dialog1.dismiss();
		}
	}

	static LayoutInflater inflater;

	/**
	 * 网络请求时的Dialog,自定义的dialog，透明背景，
	 * 
	 * @param context
	 */
	public static Dialog showDialog(Context context, String title, String message) {
		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View v = inflater.inflate(R.layout.dialog_view, null);
		TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
		TextView tv_msg = (TextView) v.findViewById(R.id.tv_msg);
		tv_title.setText(title);
		tv_msg.setText(message);
		dialog1 = new Dialog(context, R.style.translucent_notitle);
		dialog1.setContentView(v);
		dialog1.setCanceledOnTouchOutside(false);
		dialog1.show();
		return dialog1;
	}

	public static Dialog showDialogFood(final Context context, String leftmsg, String rightmsg, String message) {
		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View v = inflater.inflate(R.layout.lookmodepop, null);
		Button leftbtn = (Button) v.findViewById(R.id.daymode);
		Button rightbtn = (Button) v.findViewById(R.id.nightmode);
		TextView msg = (TextView) v.findViewById(R.id.laytext);
		msg.setText(message);
		leftbtn.setText(leftmsg);
		rightbtn.setText(rightmsg);
		leftbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(myIntent);
				dialog1.dismiss();
			}
		});
		rightbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, FoodActivity.class);
				context.startActivity(intent);
				dialog1.dismiss();
			}
		});
		dialog1 = new Dialog(context, R.style.translucent_notitle);
		dialog1.setContentView(v);
		dialog1.setCanceledOnTouchOutside(false);
		dialog1.show();
		return dialog1;
	}

	public static Dialog showDialogshop(final Context context, String leftmsg, String rightmsg, String message) {
		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View v = inflater.inflate(R.layout.lookmodepop, null);
		Button leftbtn = (Button) v.findViewById(R.id.daymode);
		Button rightbtn = (Button) v.findViewById(R.id.nightmode);
		TextView msg = (TextView) v.findViewById(R.id.laytext);
		msg.setText(message);
		leftbtn.setText(leftmsg);
		rightbtn.setText(rightmsg);
		leftbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(myIntent);
				dialog1.dismiss();
			}
		});
		rightbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, ShoppingActivity.class);
				context.startActivity(intent);
				dialog1.dismiss();
			}
		});
		dialog1 = new Dialog(context, R.style.translucent_notitle);
		dialog1.setContentView(v);
		dialog1.setCanceledOnTouchOutside(false);
		dialog1.show();
		return dialog1;
	}

	public static Dialog showDialogquanjing(final Context context, String leftmsg, String rightmsg, String message,
			final String id) {
		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View v = inflater.inflate(R.layout.lookmodepop, null);
		Button leftbtn = (Button) v.findViewById(R.id.daymode);
		Button rightbtn = (Button) v.findViewById(R.id.nightmode);
		TextView msg = (TextView) v.findViewById(R.id.laytext);
		msg.setText(message);
		leftbtn.setText(leftmsg);
		rightbtn.setText(rightmsg);
		leftbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog1.dismiss();
				Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();
				Intent intentdl = new Intent(context, DownloadService.class);
				intentdl.putExtra("url", "http://112.21.190.22/zx/zip.jsp?type=" + id);
				intentdl.putExtra("path", "/panorama_" + id + ".zip");
				intentdl.putExtra("zippath", "/panorama_" + id);
				context.startService(intentdl);
			}
		});
		rightbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog1.dismiss();
			}
		});
		dialog1 = new Dialog(context, R.style.translucent_notitle);
		dialog1.setContentView(v);
		dialog1.setCanceledOnTouchOutside(false);
		dialog1.show();
		return dialog1;
	}

	public static Dialog showDialogzhusu(final Context context, String leftmsg, String rightmsg, String message) {
		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View v = inflater.inflate(R.layout.lookmodepop, null);
		Button leftbtn = (Button) v.findViewById(R.id.daymode);
		Button rightbtn = (Button) v.findViewById(R.id.nightmode);
		TextView msg = (TextView) v.findViewById(R.id.laytext);
		msg.setText(message);
		leftbtn.setText(leftmsg);
		rightbtn.setText(rightmsg);
		leftbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(myIntent);
				dialog1.dismiss();
			}
		});
		rightbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, ZhuSuTuiJianActivity.class);
				context.startActivity(intent);
				dialog1.dismiss();
			}
		});
		dialog1 = new Dialog(context, R.style.translucent_notitle);
		dialog1.setContentView(v);
		dialog1.setCanceledOnTouchOutside(false);
		dialog1.show();
		return dialog1;
	}

	public static Dialog showDialogPhone(final Context context, String leftmsg, String rightmsg, String message,
			final String telphone) {
		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View v = inflater.inflate(R.layout.lookmodepop, null);
		Button leftbtn = (Button) v.findViewById(R.id.daymode);
		Button rightbtn = (Button) v.findViewById(R.id.nightmode);
		TextView msg = (TextView) v.findViewById(R.id.laytext);
		msg.setText(message);
		leftbtn.setText(leftmsg);
		rightbtn.setText(rightmsg);
		leftbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telphone));
				context.startActivity(intent);
				dialog1.dismiss();
			}
		});
		rightbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog1.dismiss();
			}
		});
		dialog1 = new Dialog(context, R.style.translucent_notitle);
		dialog1.setContentView(v);
		dialog1.setCanceledOnTouchOutside(true);
		dialog1.show();
		return dialog1;
	}

	/**
	 * 无网络Dialog 无网络时调用
	 * 
	 * @return Dialog 设置网络 或 取消
	 */
	public static Dialog getNoInternetDg(final Context context) {
		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View v = inflater.inflate(R.layout.lookmodepop, null);
		Button leftbtn = (Button) v.findViewById(R.id.daymode);
		Button rightbtn = (Button) v.findViewById(R.id.nightmode);
		TextView msg = (TextView) v.findViewById(R.id.laytext);
		msg.setText("暂无数据，请检查网络连接...");
		leftbtn.setText("确定");
		rightbtn.setText("取消");
		leftbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog1.dismiss();
				Intent intent = null;
				if (android.os.Build.VERSION.SDK_INT > 10)
					intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				else {
					intent = new Intent();
					ComponentName component = new ComponentName("com.android.settings",
							"com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				context.startActivity(intent);
			}
		});
		rightbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog1.dismiss();
			}
		});
		dialog1 = new Dialog(context, R.style.translucent_notitle);
		dialog1.setContentView(v);
		dialog1.setCanceledOnTouchOutside(true);
		dialog1.show();
		return dialog1;
	}
}
