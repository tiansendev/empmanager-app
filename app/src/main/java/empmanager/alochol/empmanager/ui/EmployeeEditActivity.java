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
import empmanager.alochol.empmanager.model.Employee;
import empmanager.alochol.empmanager.model.ServiceResult;
import empmanager.alochol.empmanager.util.GsonUtil;
import empmanager.alochol.empmanager.util.KeyBordHeler;
import empmanager.alochol.empmanager.util.Tools;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 编辑员工
 */
public class EmployeeEditActivity extends BaseActivity {
    public static final String EMP_ID = "EMP_ID";
    @BindView(R.id.imgTitleLeft)
    ImageView imgTitleLeft;
    @BindView(R.id.tvTitleCenter)
    TextView tvTitleCenter;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.tv_empname)
    AutoCompleteTextView tvEmpname;
    @BindView(R.id.empcode)
    AutoCompleteTextView empcode;
    @BindView(R.id.tv_age)
    EditText tvAge;
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

    private String mEmpId;
    private Context context;

    @Override
    protected void initView() {
        tvTitleCenter.setText("修改员工信息");
        loginButton.setText("提交");
    }

    @OnClick({R.id.login_button, R.id.imgTitleLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                // 校验
                if (TextUtils.isEmpty(tvEmpname.getText())) {
                    Toast.makeText(this, "员工名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(tvAge.getText()) && tvAge.getText().toString().length() > 3) {
                    Toast.makeText(this, "年龄不合法！", Toast.LENGTH_SHORT).show();
                    return;
                }
                handleEditEmp(tvEmpname.getText().toString(), tvAge.getText().toString(), rbMale.isChecked()?0:1);
                break;
            case R.id.imgTitleLeft:
                finish();
                break;
        }
    }

    private void handleEditEmp(String empName, String age, int gender) {
        Employee employee = new Employee();
        employee.setId(mEmpId);
        employee.setEmp_name(empName);
        employee.setEmp_age(Integer.valueOf(age));
        employee.setEmp_gender(gender);

        // 隐藏软件盘
        KeyBordHeler.getInstance().hideKeyBoard(tvEmpname);
        KeyBordHeler.getInstance().hideKeyBoard(tvAge);

        showLoading();

        OkHttpUtils
                .put()
                .url(Constants.URL_EMPLOYEE)
                .requestBody(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(employee)))
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
        mEmpId = getIntent().getStringExtra(EMP_ID);
        if (mEmpId != null && !TextUtils.isEmpty(mEmpId)) {
            // 获取员工信息
            obtainEmpInfoById(mEmpId);
        }
    }

    private void obtainEmpInfoById(String empId) {
        showLoading();
        OkHttpUtils
                .get()
                .url(Constants.URL_EMPLOYEE + "/" + empId)
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
                        Employee employee = GsonUtil.GsonToBean(GsonUtil.GsonString(serviceResult.getResult()), Employee.class);
                        updateUi(employee);
                    }
                });
    }

    private void updateUi(Employee employee) {
        tvEmpname.setText(employee.getEmp_name());
        tvAge.setText(String.valueOf(employee.getEmp_age()));
        Integer gender = employee.getEmp_gender();
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
        return R.layout.activity_add_emp;
    }
}
