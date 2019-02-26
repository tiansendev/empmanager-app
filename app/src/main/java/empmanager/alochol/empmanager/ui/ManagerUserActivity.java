package empmanager.alochol.empmanager.ui;

import android.content.Context;
import android.content.Intent;
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

import butterknife.BindView;
import butterknife.OnClick;
import empmanager.alochol.empmanager.R;
import empmanager.alochol.empmanager.base.BaseActivity;

public class ManagerUserActivity extends BaseActivity {
    @BindView(R.id.imgTitleLeft)
    ImageView imgTitleLeft;
    @BindView(R.id.tvTitleCenter)
    TextView tvTitleCenter;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.tv_username)
    AutoCompleteTextView tvUsername;
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


    @OnClick({R.id.btn_query, R.id.imgTitleLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                Intent intent = new Intent(context, ListUserActivity.class);
                intent.putExtra(ListUserActivity.NAME, tvUsername.getText().toString());
                intent.putExtra(ListUserActivity.MIN_AGE, etMinAge.getText().toString());
                intent.putExtra(ListUserActivity.MAX_AGE, etMaxAge.getText().toString());
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
    protected void initView() {
        tvTitleCenter.setText("查询用户");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        context = this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_manager_user;
    }

}
