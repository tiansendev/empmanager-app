package empmanager.alochol.empmanager.model;

/**
 * 登录成功返回的信息
 */
public class ManagerToken {
    private Manager manager;
    private String token;
    private boolean isAutoLogin;
    private boolean isRememberPwd;

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        isAutoLogin = autoLogin;
    }

    public boolean isRememberPwd() {
        return isRememberPwd;
    }

    public void setRememberPwd(boolean rememberPwd) {
        isRememberPwd = rememberPwd;
    }
}
