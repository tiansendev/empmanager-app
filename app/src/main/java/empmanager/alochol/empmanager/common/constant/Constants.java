package empmanager.alochol.empmanager.common.constant;

import android.text.TextUtils;

import empmanager.alochol.empmanager.base.BaseAppliction;
import empmanager.alochol.empmanager.http.SharedPreferenceHelper;


/**
 * 全局常量
 */

public class Constants {
    private static String sIp;

    private static String sPort;

    public static String BASE_URL;

    private static SharedPreferenceHelper sp;


    static {
        sp =  SharedPreferenceHelper.getInstance(BaseAppliction.getInstance());
        sIp = sp.getIp();
        sPort = sp.getPort();
        if (TextUtils.isEmpty(sIp) || TextUtils.isEmpty(sPort)) {
//            BASE_URL = "http://172.16.63.40:8195";
            BASE_URL = "http://www.esimtek.cn:8195";
        } else {
            BASE_URL = "http://" + sIp + ":" + sPort;
        }
    }

    /** 系统管理员 */
    public static final Integer SYSTEM_MGR = 100;

    /** 普通注册用户 */
    public static final Integer COMMON_MGR = 99;

    public static final String URL_REGISTER = BASE_URL + "/manager/register";

    public final static String URL_LOGIN = BASE_URL + "/manager/login";

    public static final String URL_MANAGER = BASE_URL + "/manager";

    public static final String URL_MANAGER_BY_PAGE = BASE_URL + "/manager/bypage";

    public static final String URL_EMPLOYEE = BASE_URL + "/employee";

    public static final String URL_EMPLOYEE_BY_PAGE = BASE_URL + "/employee/bypage";


}
