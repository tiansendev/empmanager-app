package empmanager.alochol.empmanager.model;


/**
 * 全局状态码
 */
public enum ServiceResultCode {

    /**
     * 通用
     */
    SUCCESS("200000", "success"),
    PERMISSION_NO_ENOUGH("200001", "权限不足"),
    PARAM_EXCEPTION("200002", "参数异常"),
    TOKEN_EXPIRE("2000A0", "未登录或token过期");


    private String code;
    private String message;

    private ServiceResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ServiceResultCode getByCode(String code) {
        for (ServiceResultCode c : ServiceResultCode.values()) {
            if (c.code.equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (ServiceResultCode c : ServiceResultCode.values()) {
            if (c.code.equalsIgnoreCase(code)) {
                return c.message;
            }
        }
        return null;
    }
}
