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
import empmanager.alochol.empmanager.model.Employee;
import empmanager.alochol.empmanager.model.ServiceResult;
import empmanager.alochol.empmanager.util.GsonUtil;
import empmanager.alochol.empmanager.util.KeyBordHeler;
import empmanager.alochol.empmanager.util.Tools;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * 添加员工
 */
public class AddEmployeeActivity extends BaseActivity {
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

    private Context context;

    @OnClick({R.id.login_button, R.id.imgTitleLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                if (TextUtils.isEmpty(tvEmpname.getText())) {
                    Toast.makeText(this, "员工名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(tvAge.getText()) && tvAge.getText().toString().length() > 3) {
                    Toast.makeText(this, "年龄不合法！", Toast.LENGTH_SHORT).show();
                    return;
                }
                handleAddEmp(tvEmpname.getText().toString(), tvAge.getText().toString(), rbMale.isChecked()?0:1);
                break;
            case R.id.imgTitleLeft:
                finish();
                break;
        }
    }

    private void handleAddEmp(String empName, String age, int gender) {
        Employee employee = new Employee();
        employee.setEmp_name(empName);
        employee.setEmp_age(Integer.valueOf(age));
        employee.setEmp_gender(gender);

        // 隐藏软件盘
        KeyBordHeler.getInstance().hideKeyBoard(tvEmpname);
        KeyBordHeler.getInstance().hideKeyBoard(tvAge);

        showLoading();

        OkHttpUtils
                .postString()
                .url(Constants.URL_EMPLOYEE)
                .content(new Gson().toJson(employee))
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
        tvTitleCenter.setText("添加员工");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        context = this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_emp;
    }

}
