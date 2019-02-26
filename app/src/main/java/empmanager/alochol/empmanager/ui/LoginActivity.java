package empmanager.alochol.empmanager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import empmanager.alochol.empmanager.R;
import empmanager.alochol.empmanager.base.BaseActivity;
import empmanager.alochol.empmanager.base.BaseAppliction;
import empmanager.alochol.empmanager.common.constant.Constants;
import empmanager.alochol.empmanager.http.SharedPreferenceHelper;
import empmanager.alochol.empmanager.model.Manager;
import empmanager.alochol.empmanager.model.ManagerToken;
import empmanager.alochol.empmanager.model.ServiceResult;
import empmanager.alochol.empmanager.model.ServiceResultCode;
import empmanager.alochol.empmanager.util.GsonUtil;
import empmanager.alochol.empmanager.util.KeyBordHeler;
import empmanager.alochol.empmanager.util.Tools;
import okhttp3.Call;
import okhttp3.MediaType;

import static android.content.ContentValues.TAG;

public class LoginActivity extends BaseActivity {

    public Context context = this;
    @BindView(R.id.username)
    AutoCompleteTextView usernameEt;
    @BindView(R.id.password)
    EditText passwordEt;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.cb_remember_pwd)
    CheckBox cbRememberPwd;
    @BindView(R.id.cb_auto_login)
    CheckBox cbAutoLogin;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.activity_login_ll)
    RelativeLayout activityLoginLl;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void initView() {
        cbRememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    cbAutoLogin.setChecked(true);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (BaseAppliction.sUser != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        autoLogin();
    }

    private void autoLogin() {
        ManagerToken managerToken = SharedPreferenceHelper.getInstance(context).getLoginUser();
        Manager manager = managerToken.getManager();
        if (manager != null && !TextUtils.isEmpty(manager.getMgr_name()) && !TextUtils.isEmpty(manager.getMgr_password())) {
            Log.d(TAG, "==== auto login params ====> \n mobile: " + manager.getMgr_password());
            if (managerToken.isRememberPwd()) {
                usernameEt.setText(manager.getMgr_name());
                passwordEt.setText(manager.getMgr_password());
                cbRememberPwd.setChecked(true);

                if (managerToken.isAutoLogin()) {
                    handleLogin(manager.getMgr_name(), manager.getMgr_password());
                    cbAutoLogin.setChecked(true);
                } else
                    cbAutoLogin.setChecked(false);
            } else {
                cbRememberPwd.setChecked(false);
                cbAutoLogin.setChecked(false);
                usernameEt.setText("");
                passwordEt.setText("");
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.login_button, R.id.tv_register})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.login_button:

                if (TextUtils.isEmpty(usernameEt.getText())) {
                    Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(passwordEt.getText())) {
                    Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                handleLogin(usernameEt.getText().toString(), passwordEt.getText().toString());
                break;
                case R.id.tv_register:
                    startActivity(new Intent(context, RegisterActivity.class));
                    break;
        }
    }

    private void handleLogin(final String username, final String password) {
        Manager user = new Manager(username, password);

        // 隐藏软件盘
        KeyBordHeler.getInstance().hideKeyBoard(usernameEt);
        KeyBordHeler.getInstance().hideKeyBoard(passwordEt);

        showLoading();

        OkHttpUtils
                .postString()
                .url(Constants.URL_LOGIN)
                .content(new Gson().toJson(user))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        hideLoading();
                        Tools.showInfoShort(LoginActivity.this, "登录失败: " + e.getMessage());
                        context.startActivity(new Intent(context, HomeActivity.class));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoading();
                        handleRes(response);
                    }
                });

    }

    private void handleRes(final String response) {
        ServiceResult userRes = GsonUtil.GsonToBean(response, ServiceResult.class);

        if (userRes.getCode().equals(ServiceResultCode.SUCCESS.getCode())) {
            ManagerToken managerToken = GsonUtil.GsonToBean(GsonUtil.GsonString(userRes.getResult()), ManagerToken.class);


            BaseAppliction.sUser = managerToken.getManager();

            BaseAppliction.sIntercepter.setToken(managerToken.getToken());

            // 保存到sp
            BaseAppliction.sUser.setMgr_password(passwordEt.getText().toString());
            managerToken.setAutoLogin(cbAutoLogin.isChecked());
            managerToken.setRememberPwd(cbRememberPwd.isChecked());
            SharedPreferenceHelper.getInstance(context).putUser(managerToken);

            //jump to homeActivity
            context.startActivity(new Intent(context, HomeActivity.class));
            finish();
        } else {
            Tools.showInfoShort(context, userRes.getMessage());
        }
    }


    /*------------------------------------------- ip端口设置 ----------------------------------------*/
//    private void showSettingDialog() {
//        final MaterialDialog dialog = new MaterialDialog(this);
//        View v = LayoutInflater.from(this).inflate(R.layout.url_setting, null);
//        final EditText etIp = (EditText) v.findViewById(R.id.et_ip);
//        final EditText etPort = (EditText) v.findViewById(R.id.et_port);
//        dialog.setContentView(v);
//        String spIp = SharedPreferenceHelper.getInstance(this).getIp();
//        String spPort = SharedPreferenceHelper.getInstance(this).getPort();
//        if (!TextUtils.isEmpty(spIp)) {
//            etIp.setText(spIp);
//        }
//        if (!TextUtils.isEmpty(spPort)) {
//            etPort.setText(spPort);
//        }
//        dialog.setCanceledOnTouchOutside(false)
//                .setTitle("提示")
//                .setPositiveButton("确定", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (TextUtils.isEmpty(etIp.getText())) {
//                            Tools.showInfoShort(LoginActivity.this, "ip地址不能为空！");
//                            return;
//                        }
//                        if (TextUtils.isEmpty(etPort.getText())) {
//                            Tools.showInfoShort(LoginActivity.this, "端口号不能为空！");
//                            return;
//                        }
//                        if (invalidUrl("https://" + etIp.getText().toString() + ":" +etPort.getText().toString())) return;
//                        SharedPreferenceHelper.getInstance(LoginActivity.this).putIp(etIp.getText().toString());
//                        SharedPreferenceHelper.getInstance(LoginActivity.this).putPort(etPort.getText().toString());
//                        dialog.dismiss();
//                        showRestartDialog();
//                    }
//                }).setNegativeButton("取消", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tools.hideLoadingView();
//                dialog.dismiss();
//            }
//        }).show();
//    }
//
//    private void showRestartDialog() {
//        final MaterialDialog dialog = new MaterialDialog(this)
//                .setTitle("提示")
//                .setMessage("ip地址、端口号修改成功，重启后生效！");
//
//        dialog.setPositiveButton("重启", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tools.restartApp(LoginActivity.this);
//                dialog.dismiss();
//            }
//        }).setNegativeButton("取消", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        }).setCanceledOnTouchOutside(false);
//        dialog.show();
//    }
//
//    private boolean invalidUrl(String url) {
//        // Silently replace websocket URLs with HTTP URLs.
//        if (TextUtils.isEmpty(url)) {
//            Tools.showInfoShort(this, "请设置ip地址、端口号！");
//            return true;
//        }
//
//        if (url.regionMatches(true, 0, "ws:", 0, 3)) {
//            url = "http:" + url.substring(3);
//        } else if (url.regionMatches(true, 0, "wss:", 0, 4)) {
//            url = "https:" + url.substring(4);
//        }
//
//        HttpUrl parsed = HttpUrl.parse(url);
//        if (parsed == null) {
//            Tools.showInfoShort(this, "ip地址、端口号设置有误！");
//            return true;
//        }
//        return false;
//    }
//
//    private void showRestartDialog() {
//        final MaterialDialog dialog = new MaterialDialog(this)
//                .setTitle("提示")
//                .setMessage("ip地址、端口号修改成功，重启后生效！");
//
//        dialog.setPositiveButton("重启", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tools.restartApp(LoginActivity.this);
//                dialog.dismiss();
//            }
//        }).setNegativeButton("取消", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        }).setCanceledOnTouchOutside(false);
//        dialog.show();
//    }
}
