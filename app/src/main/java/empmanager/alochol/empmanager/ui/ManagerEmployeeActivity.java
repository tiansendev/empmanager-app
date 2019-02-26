package empmanager.alochol.empmanager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import empmanager.alochol.empmanager.R;
import empmanager.alochol.empmanager.base.BaseActivity;

/**
 * 员工管理界面
 */
public class ManagerEmployeeActivity extends BaseActivity {
    @BindView(R.id.imgTitleLeft)
    ImageView imgTitleLeft;
    @BindView(R.id.tvTitleCenter)
    TextView tvTitleCenter;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.tv_empname)
    AutoCompleteTextView tvEmpname;
    @BindView(R.id.et_min_age)
    EditText etMinAge;
    @BindView(R.id.et_max_age)
    EditText etMaxAge;
    @BindView(R.id.rb_all)
    RadioButton rbAll;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_famale)
    RadioButton rbFamale;
    @BindView(R.id.btn_query)
    Button btnQuery;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.activity_login_ll)
    RelativeLayout activityLoginLl;
    private Context context;

    @Override
    protected void initView() {
        tvTitleCenter.setText("查询员工");
    }

    @OnClick({R.id.btn_query, R.id.imgTitleLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                Intent intent = new Intent(context, ListEmpActivity.class);
                intent.putExtra(ListEmpActivity.NAME, tvEmpname.getText().toString());
                intent.putExtra(ListEmpActivity.MIN_AGE, etMinAge.getText().toString());
                intent.putExtra(ListEmpActivity.MAX_AGE, etMaxAge.getText().toString());
                Integer gender = null;
                if (rbMale.isChecked()) {
                    gender = 0;
                } else if (rbFamale.isChecked()) {
                    gender = 1;
                }
                intent.putExtra(ListUserActivity.GENDER, gender);
                startActivity(intent);
                break;
            case R.id.imgTitleLeft:
                finish();
                break;
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        context = this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_manager_emp;
    }

}
