package empmanager.alochol.empmanager.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.Toast;

import empmanager.alochol.empmanager.ui.LoginActivity;


public class Tools {

	private static Dialog dialog;

	public static String getCurrentVersion(Context context)
			throws NameNotFoundException {
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();
		PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
		return packageInfo.versionName;

	}

	private static long oneTime;
	private static long twoTime;
	private static String oldMsg;
	protected static Toast toast = null;

	/**
	 * toast long
	 * @param context
	 * @param msg
	 */
	public static void showInfoLong(Context context, String msg) {
		_showInfo(context, msg, 1);
	}

	/**
	 * toast short
	 * @param context
	 * @param msg
	 */
	public static void showInfoShort(Context context, String msg) {
		_showInfo(context, msg, -1);
	}

	/**
	 * 防止同样的信息连续多次触发重复弹出
	 * @param context
	 * @param msg
	 * @param i
	 */
	private static void _showInfo(Context context, String msg, int i) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, i > 0 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
//			toast.setMargin(1/14f,0);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (msg.equals(oldMsg)) {
				if (twoTime - oneTime > (i > 0 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)) {
					toast.show();
				}
			} else {
				oldMsg = msg;
				toast.setText(msg);
//				toast.setMargin(1/14f,0);
				toast.show();
			}
			oneTime = twoTime;
		}
	}

//	/**
//	 * 显示进度条
//	 *
//	 * @param activity
//	 */
//	public static void showLoadingView(Activity activity) {
//		try {
//			if (dialog == null) {
//				dialog = new Dialog(activity, R.style.new_circle_progress);
//				dialog.setContentView(R.layout.layout_progressbar);
//				dialog.show();
//				dialog.setCancelable(false);
//				dialog.setCanceledOnTouchOutside(false);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static void hideLoadingView() {
		if (dialog != null) {
			dialog.cancel();
			dialog = null;
			System.gc();
		}
	}

	public static boolean isLoadingViewShow() {
		if (dialog != null && dialog.isShowing()) {
			return true;
		}
		return false;
	}


	/**
	 * the pending intent to start the activity
	 * @param activity
	 */
	public static void restartApp(Activity activity) {
		Intent restartIntent = new Intent(activity, LoginActivity.class);
		int pendingId = 1;
		PendingIntent pendingIntent = PendingIntent.getActivity(
				activity, pendingId, restartIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager mgr = (AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
		activity.finish();
		System.exit(0);
	}


}
