package empmanager.alochol.empmanager.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import empmanager.alochol.empmanager.http.LogCatStrategy;
import empmanager.alochol.empmanager.http.LoggingInterceptor;
import empmanager.alochol.empmanager.http.SharedPreferenceHelper;
import empmanager.alochol.empmanager.http.TokenInterceptor;
import empmanager.alochol.empmanager.model.Manager;
import empmanager.alochol.empmanager.model.ManagerToken;
import empmanager.alochol.empmanager.ui.LoginActivity;
import okhttp3.OkHttpClient;

/**
 * 自定义Application
 */
public class BaseAppliction extends Application {
    private static BaseAppliction instance;
    /**
     * 用户信息
     */
    public static Manager sUser;

    /**
     * token拦截器
     */
    public static TokenInterceptor sIntercepter;

    /**
     * activity栈
     */
    public static ArrayList<Activity> sActivityStack;

    public static Context getInstance() {
        return instance;
    }

    /**
     * 退出应用 并且清空xml中用户信息
     */
    public static void exitAppAndClearLoginInfo() {
        for (Activity activity : sActivityStack) {
            if (activity != null && !activity.isDestroyed())
                activity.finish();
        }
        SharedPreferenceHelper.getInstance(getInstance()).putUser(new ManagerToken());
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sActivityStack = new ArrayList<>();
        instance = this;
        initLogger();
        initOkhttp();
    }

    /**
     * 初始化Logger
     */
    private void initLogger() {
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .logStrategy(new LogCatStrategy())
                .tag("EMPMGR_HTTP")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(strategy));
    }

    /**
     * 初始化OKHTTP
     */
    public static void initOkhttp() {
        sIntercepter = new TokenInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(sIntercepter)
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 跳到登录页面
     */
    public static void jumpToLoginPage() {
        sUser = null;
        Intent intent = new Intent(getInstance(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getInstance().startActivity(intent);
    }
}
