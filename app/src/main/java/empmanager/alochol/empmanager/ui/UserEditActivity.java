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
import okhttp3.RequestBody;


public class UserEditActivity extends BaseActivity {
    public static final String MANANGER_ID = "MANANGER_ID";
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

    private String mManagerId;
    private Context context;

    @Override
    protected void initView() {
        tvTitleCenter.setText("修改用户信息");
        btnAdd.setText("提交");
        context = this;
    }

    @OnClick({R.id.btn_add, R.id.imgTitleLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                // 校验
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
                handleEditManager(tvUsername.getText().toString(), tvPwd.getText().toString(), tvAge.getText().toString(), rbMale.isChecked() ? 0 : 1);
                break;
            case R.id.imgTitleLeft:
                finish();
                break;
        }
    }

    private void handleEditManager(String username, String pwd, String age, int gender) {
        Manager manager = new Manager(username, pwd);
        manager.setId(mManagerId);
        manager.setMgr_age(Integer.valueOf(age));
        manager.setGender(gender);

        // 隐藏软件盘
        KeyBordHeler.getInstance().hideKeyBoard(tvUsername);
        KeyBordHeler.getInstance().hideKeyBoard(tvPwd);
        KeyBordHeler.getInstance().hideKeyBoard(tvAge);

        showLoading();

        OkHttpUtils
                .put()
                .url(Constants.URL_MANAGER)
                .requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(manager)))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        hideLoading();
                        Tools.showInfoShort(context, "修改失败: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoading();
                        ServiceResult serviceResult = GsonUtil.GsonToBean(response, ServiceResult.class);
                        if (!serviceResult.isSuccess()) {
                            Tools.showInfoShort(context, "修改失败: " + serviceResult.getMessage());
                            return;
                        }
                        Tools.showInfoShort(context, "修改成功");
                        finish();
                    }
                });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        context = this;
        mManagerId = getIntent().getStringExtra(MANANGER_ID);
        if (mManagerId != null && !TextUtils.isEmpty(mManagerId)) {
            // 获取员工信息
            obtainManagerInfoById(mManagerId);
        }
    }

    private void obtainManagerInfoById(String ManagerId) {
        showLoading();
        OkHttpUtils
                .get()
                .url(Constants.URL_MANAGER + "/" + ManagerId)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        hideLoading();
                        Tools.showInfoShort(context, "查询失败: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoading();
                        ServiceResult serviceResult = GsonUtil.GsonToBean(response, ServiceResult.class);
                        if (!serviceResult.isSuccess()) {
                            Tools.showInfoShort(context, "查询失败: " + serviceResult.getMessage());
                            return;
                        }
                        Manager manager = GsonUtil.GsonToBean(GsonUtil.GsonString(serviceResult.getResult()), Manager.class);
                        updateUi(manager);
                    }
                });
    }

    private void updateUi(Manager manager) {
        tvUsername.setText(manager.getMgr_name());
        tvPwd.setText(manager.getMgr_password());
        tvAge.setText(String.valueOf(manager.getMgr_age()));
        Integer gender = manager.getGender();
        switch (gender) {
            case 0:
                rbMale.setChecked(true);
                break;
            case 1:
                rbFamale.setChecked(true);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_user;
    }

}
