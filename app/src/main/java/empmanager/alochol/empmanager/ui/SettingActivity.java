package empmanager.alochol.empmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import empmanager.alochol.empmanager.R;
import empmanager.alochol.empmanager.base.BaseActivity;
import empmanager.alochol.empmanager.base.BaseAppliction;
import empmanager.alochol.empmanager.common.constant.Constants;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.imgTitleLeft)
    ImageView imgTitleLeft;
    @BindView(R.id.tvTitleCenter)
    TextView tvTitleCenter;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.update_indo)
    Button updateIndo;
    @BindView(R.id.exit)
    Button exit;

    @OnClick({R.id.imgTitleLeft, R.id.exit, R.id.update_indo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgTitleLeft:
                finish();
                break;
            case R.id.update_indo:
                // 更新用户信息
                String id = BaseAppliction.sUser.getId();
                Intent intent = new Intent(this, UserEditActivity.class);
                intent.putExtra(UserEditActivity.MANANGER_ID, id);
                startActivity(intent);
                break;
            case R.id.exit:
                BaseAppliction.exitAppAndClearLoginInfo();
                break;
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        if (BaseAppliction.sUser.getRole_id() == Constants.SYSTEM_MGR) {
//            // 是主管理员
//            updateIndo.setEnabled(false);
//        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

}
