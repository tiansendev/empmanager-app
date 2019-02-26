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
import butterknife.ButterKnife;
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

public class AddUserActivity extends BaseActivity {


    @BindView(R.id.imgTitleLeft)
    ImageView imgTitleLeft;
    @BindView(R.id.tvTitleCenter)
    TextView tvTitleCenter;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.tv_username)
    AutoCompleteTextView tvUsername;
    @BindView(R.id.tv_pwd)
    AutoCompleteTextView tvPwd;
    @BindView(R.id.usercode)
    AutoCompleteTextView usercode;
    @BindView(R.id.tv_age)
    EditText tvAge;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_famale)
    RadioButton rbFamale;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.activity_login_ll)
    RelativeLayout activityLoginLl;
    private Context context;


    @OnClick({R.id.btn_add, R.id.imgTitleLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                if (TextUtils.isEmpty(tvUsername.getText())) {
                    Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tvPwd.getText())) {
                    Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(tvAge.getText()) && tvAge.getText().toString().length() > 3) {
                    Toast.makeText(this, "年龄不合法！", Toast.LENGTH_SHORT).show();
                    return;
                }
                handleAddUser(tvUsername.getText().toString(), tvPwd.getText().toString(), tvAge.getText().toString(), rbMale.isChecked() ? 0 : 1);
                break;
            case R.id.imgTitleLeft:
                finish();
                break;
        }
    }

    private void handleAddUser(String username, String pwd, String age, int gender) {
        Manager manager = new Manager(username, pwd);
        manager.setMgr_age(Integer.valueOf(age));
        manager.setGender(gender);

        // 隐藏软件盘
        KeyBordHeler.getInstance().hideKeyBoard(tvUsername);
        KeyBordHeler.getInstance().hideKeyBoard(tvPwd);
        KeyBordHeler.getInstance().hideKeyBoard(tvAge);

        showLoading();

        OkHttpUtils
                .postString()
                .url(Constants.URL_MANAGER)
                .content(new Gson().toJson(manager))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        hideLoading();
                        Tools.showInfoShort(context, "添加失败: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoading();
                        ServiceResult serviceResult = GsonUtil.GsonToBean(response, ServiceResult.class);
                        if (!serviceResult.isSuccess()) {
                            Tools.showInfoShort(context, "添加失败: " + serviceResult.getMessage());
                            return;
                        }
                        Tools.showInfoShort(context, "添加成功");
                        finish();
                    }
                });
    }

    @Override
    protected void initView() {
        tvTitleCenter.setText("添加用户");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        context = this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_user;
    }

}
