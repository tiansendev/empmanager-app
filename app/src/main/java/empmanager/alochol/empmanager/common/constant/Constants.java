package empmanager.alochol.empmanager.common.constant;

import android.text.TextUtils;

import empmanager.alochol.empmanager.base.BaseAppliction;
import empmanager.alochol.empmanager.http.SharedPreferenceHelper;


/**
 * Created by tiansen on 18-3-26.
 */

public class Constants {
    private static String sIp;

    private static String sPort;

    public static String BASE_URL;

    public static String BASE_IMG_URL;

    private static SharedPreferenceHelper sp;


    public static final Integer SYSTEM_MGR = 100;
    public static final Integer COMMON_MGR = 99;

    static {
        sp =  SharedPreferenceHelper.getInstance(BaseAppliction.getInstance());
        sIp = sp.getIp();
        sPort = sp.getPort();
        if (TextUtils.isEmpty(sIp) || TextUtils.isEmpty(sPort)) {
            BASE_URL = "http://172.16.63.40:8090";
        } else {
            BASE_URL = "http://" + sIp + ":" + sPort;
        }
    }

//    public final static String BASE_URL = "https://assets.esimtekiot.com:8443/assetsmanager/api/v1/";
//    public static String BASE_URL = "http://172.16.63.193:8090/api/v1/";
//    public final static String BASE_URL = "http://172.16.63.33:8195/api/v1/";

    public static final String URL_REGISTER = BASE_URL + "/manager/register";

    public final static String URL_LOGIN = BASE_URL + "/manager/login";

    public static final String URL_MANAGER = BASE_URL + "/manager";
    public static final String URL_MANAGER_BY_PAGE = BASE_URL + "/manager/bypage";

    public static final String URL_EMPLOYEE = BASE_URL + "/employee";
    public static final String URL_EMPLOYEE_BY_PAGE = BASE_URL + "/employee/bypage";

//    public static final String URL_POSITION_DETAILS = BASE_URL + "/positions/details";
//
//    public static final String URL_NFC_DETAIL = BASE_URL + "/nfcs/details";
//
//    public static final String URL_NFC = BASE_URL + "/nfcs";
//
//    public static final String URL_GET_RAILWAYS = BASE_URL + "/railways";
//
//    public static final String URL_GET_LINES = BASE_URL + "/lines";
//
//    public static final String URL_UPLOAD_IMG = BASE_URL + "/positions/uploadimgs";
//
//    public static final String URL_POSITION = BASE_URL + "/positions";


//    public final static String LOCATIONS_URL = BASE_URL + "locationareas";
//
//    /**
//     * crops
//     */
//    public final static String URL_GET_CROPS = BASE_URL + "auth/manager/corps";
//    public final static String URL_CHOOSE_CROP(String id) {
//        return URL_GET_CROPS + "/" + id + "/choice";
//    }
//
//    /**
//     * inventorys
//     */
//    public final static String URL_GET_INVS = BASE_URL + "inventoryorders";
//    public final static String URL_GET_INV_DETAILS(String id) {
//        return URL_GET_INVS + "/" + id + "/detail";
//    }
//
//    public final static String URL_COMMIT_INV_DETAILS(String id) {
//        return URL_GET_INVS + "/" + id + "/commit";
//    }
//    public final static String URL_FINISH_INV(String id) {
//        return URL_GET_INVS + "/" + id + "/finish";
//    }
//
//    /**
//     * products
//     */
//    public final static String URL_GET_PRODS = BASE_URL + "products/unpage";
//    public static final String URL_UPLOAD_IMG = BASE_URL + "products/uploadimg";
//    public static final String URL_PROD_IMG_URLS = BASE_URL + "products/imgurl";
//    public final static String URL_GET_PROD_BYID(String id) {
//        return URL_GET_PRODS + "/" + id;
//    }
//    public final static String URL_UPDATE_PROD(String id) {
//        return BASE_URL + "products/" + id;
//    }
//    public final static String URL_GET_PRODS_BY_RFID = BASE_URL + "products/byrfids";
//
//    public final static String URL_PUT_ENTER_PRODS(String storeid) {
//        return BASE_URL + "products/instore?areaid=" + storeid;
//    }
//
//    /**
//     * stores
//     */
//    public static final String URL_GET_STORES = BASE_URL + "stores";
//    public final static String URL_GET_STORE_BYID(String id) {
//        return URL_GET_STORES + "/" + id;
//    }
//
//    /**
//     * employees
//     */
//    public static final String URL_GET_EMPS = BASE_URL + "employees";
//    public final static String URL_GET_EMP_BYID(String id) {
//        return URL_GET_EMPS + "/" + id;
//    }
//
//    /**
//     * requisition
//     */
//    public static final String URL_GET_REQUISITIONS = BASE_URL + "orders/requisitions";
//    public final static String URL_POST_REQ_PRODS(String odId) {
//        return URL_GET_REQUISITIONS + "/" + odId;
//    }
//
//    /**
//     * back into store
//     */
//    public static final String URL_WARE_HOUSE_BACKS = BASE_URL + "orders/warehousebacks";




}
