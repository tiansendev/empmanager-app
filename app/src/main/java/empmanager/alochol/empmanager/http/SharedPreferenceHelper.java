package empmanager.alochol.empmanager.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import empmanager.alochol.empmanager.model.ManagerToken;
import empmanager.alochol.empmanager.util.GsonUtil;

/**
 * SharedPreference工具类
 */
public class SharedPreferenceHelper {
	SharedPreferences sp;
	SharedPreferences.Editor editor;

	private static SharedPreferenceHelper manager = null;

	@SuppressLint("CommitPrefEdits")
	private SharedPreferenceHelper(Context context) {
		sp = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		editor = sp.edit();
	}

	public static SharedPreferenceHelper getInstance(Context context) {
		if (manager == null) {
			manager = new SharedPreferenceHelper(context);
		}
		return manager;
	}

	public void put(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * put ip
	 */
	public void putIp(String account) {
		put("ip", account);
	}

	/**
	 * get ip
	 */
	public String getIp() {
		return get("ip", "");
	}

	/**
	 * put port
	 */
	public void putPort(String account) {
		put("port", account);
	}

	/**
	 * get port
	 */
	public String getPort() {
		return get("port", "");
	}

	//------------------------- Common func -----------------------------

	public void put(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}


	public void put(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public void put(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public String get(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	public int get(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	public long get(String key, long defValue) {
		return sp.getLong(key, defValue);
	}

	public boolean get(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}


	public ManagerToken getLoginUser() {
		return GsonUtil.GsonToBean(get("user", GsonUtil.GsonString(new ManagerToken())), ManagerToken.class);
	}

	public void putUser(ManagerToken user) {
		put("user", GsonUtil.GsonString(user));
	}
}
