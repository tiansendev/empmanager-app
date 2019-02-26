package empmanager.alochol.empmanager.util;

import android.content.Context;
import android.widget.Toast;


public class Tools {

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
				toast.show();
			}
			oneTime = twoTime;
		}
	}

}
