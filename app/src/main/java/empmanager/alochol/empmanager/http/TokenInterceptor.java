package empmanager.alochol.empmanager.http;

import android.util.Log;

import java.io.IOException;

import empmanager.alochol.empmanager.base.BaseAppliction;
import empmanager.alochol.empmanager.model.ManagerToken;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Token拦截器
 */
public final class TokenInterceptor implements Interceptor {
    private static final String TAG = "TokenInterceptor";
    private static final String USER_TOKEN = "Authorization";
    private String token;

    /**
     * 登录成功时设值
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 拦截请求
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "token intercepted: " + this.token);
        final Request originalRequest = chain.request();
        if (token == null) {
            // 从sp中取用户信息
            ManagerToken loginUser = SharedPreferenceHelper.getInstance(BaseAppliction.getInstance()).getLoginUser();
            if (loginUser != null)
                token = loginUser.getToken();
        }
        // token仍是空 或者 Authorization不是空 继续执行
        if(token == null || originalRequest.header(USER_TOKEN) != null){
            return chain.proceed(originalRequest);
        }
        // token不是空 且 Authorization是空时，请求头中添加token
        Request request = originalRequest.newBuilder()
                .header(USER_TOKEN,token)
                .build();
        return chain.proceed(request);
    }
}
