package empmanager.alochol.empmanager.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import empmanager.alochol.empmanager.R;
import empmanager.alochol.empmanager.base.BaseActivity;
import empmanager.alochol.empmanager.common.constant.Constants;
import empmanager.alochol.empmanager.model.Manager;
import empmanager.alochol.empmanager.model.ServiceResult;
import empmanager.alochol.empmanager.util.GsonUtil;
import empmanager.alochol.empmanager.util.KeyBordHeler;
import empmanager.alochol.empmanager.util.Tools;
import okhttp3.Call;
import okhttp3.MediaType;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.imgTitleLeft)
    ImageView imgTitleLeft;
    @BindView(R.id.tvTitleCenter)
    TextView tvTitleCenter;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.age)
    EditText age;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_famale)
    RadioButton rbFamale;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.activity_login_ll)
    RelativeLayout activityLoginLl;

    private Context context = this;

    @Override
    protected void initView() {
        tvTitleCenter.setText("注册");
    }

    @OnClick({R.id.login_button, R.id.imgTitleLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                // 注册
                if (TextUtils.isEmpty(username.getText())) {
                    Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(age.getText()) && age.getText().toString().length() > 3) {
                    Toast.makeText(this, "年龄不合法！", Toast.LENGTH_SHORT).show();
                    return;
                }
                handleRegister(username.getText().toString(), password.getText().toString(), age.getText().toString(), rbMale.isChecked()?0:1);
                break;
            case R.id.imgTitleLeft:
                finish();
                break;
        }
    }

    private void handleRegister(String name, String pwd, String age, Integer gender) {
        Manager user = new Manager(name, pwd);
        user.setMgr_age(Integer.valueOf(age));
        user.setGender(gender);

        // 隐藏软件盘
        KeyBordHeler.getInstance().hideKeyBoard(username);
        KeyBordHeler.getInstance().hideKeyBoard(password);

        showLoading();

        OkHttpUtils
                .postString()
                .url(Constants.URL_REGISTER)
                .content(new Gson().toJson(user))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        hideLoading();
                        Tools.showInfoShort(context, "注册失败: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
//                        handleRes(response);
                        hideLoading();
                        ServiceResult serviceResult = GsonUtil.GsonToBean(response, ServiceResult.class);
                        if (!serviceResult.isSuccess()) {
                            Tools.showInfoShort(context, "注册失败: " + serviceResult.getMessage());
                            return;
                        }
                        Tools.showInfoShort(context, "注册成功");
                        finish();
                    }
                });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

}
