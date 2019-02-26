package empmanager.alochol.empmanager.http;

import android.util.Log;

import java.io.IOException;

import empmanager.alochol.empmanager.base.BaseAppliction;
import empmanager.alochol.empmanager.model.ManagerToken;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class TokenInterceptor implements Interceptor {
    private static final String TAG = "TokenInterceptor";
    private static final String USER_TOKEN = "Authorization";
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "token intercepted: " + this.token);
        final Request originalRequest = chain.request();
        if (token == null) {
            ManagerToken loginUser = SharedPreferenceHelper.getInstance(BaseAppliction.getInstance()).getLoginUser();
            if (loginUser != null)
                token = loginUser.getToken();
        }
        if(token == null || originalRequest.header("Authorization") != null){
            return chain.proceed(originalRequest);
        }
        Request request = originalRequest.newBuilder()
                .header(USER_TOKEN,token)
                .build();
        return chain.proceed(request);
    }
}
