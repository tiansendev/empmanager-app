package empmanager.alochol.empmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import empmanager.alochol.empmanager.R;
import empmanager.alochol.empmanager.base.BaseActivity;
import empmanager.alochol.empmanager.base.BaseAppliction;
import empmanager.alochol.empmanager.common.constant.Constants;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.btn_add_emp)
    Button btnAddEmp;
    @BindView(R.id.btn_mgr_emp)
    Button btnMgrEmp;
    @BindView(R.id.btn_add_user)
    Button btnAddUser;
    @BindView(R.id.btn_mgr_user)
    Button btnMgrUser;

    @OnClick({R.id.tv_setting, R.id.tv_name, R.id.btn_add_emp, R.id.btn_add_user, R.id.btn_mgr_emp, R.id.btn_mgr_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setting:
                switchActivity(SettingActivity.class);
                break;
            case R.id.btn_add_emp:
                switchActivity(AddEmployeeActivity.class);
                break;
            case R.id.btn_add_user:
                switchActivity(AddUserActivity.class);
                break;
            case R.id.btn_mgr_emp:
                switchActivity(ManagerEmployeeActivity.class);
                break;
            case R.id.btn_mgr_user:
                switchActivity(ManagerUserActivity.class);
                break;
        }
    }

    private void switchActivity(Class<?> settingActivityClass) {
        startActivity(new Intent(this, settingActivityClass));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (BaseAppliction.sUser != null) {
            tvName.setText(BaseAppliction.sUser.getMgr_name());
            if (BaseAppliction.sUser.getRole_id() == Constants.SYSTEM_MGR) {
                // 是超级管理员
                btnAddUser.setVisibility(View.VISIBLE);
                btnMgrUser.setVisibility(View.VISIBLE);
            } else {
                btnAddUser.setVisibility(View.GONE);
                btnMgrUser.setVisibility(View.GONE);
            }
        } else {
            BaseAppliction.jumpToLoginPage();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


}
