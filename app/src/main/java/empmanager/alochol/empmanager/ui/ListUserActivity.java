package empmanager.alochol.empmanager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import empmanager.alochol.empmanager.R;
import empmanager.alochol.empmanager.base.BasePageListActivity;
import empmanager.alochol.empmanager.common.constant.Constants;
import empmanager.alochol.empmanager.model.Manager;
import empmanager.alochol.empmanager.model.PageInfo;
import empmanager.alochol.empmanager.model.ServiceResult;
import empmanager.alochol.empmanager.util.GsonUtil;
import empmanager.alochol.empmanager.util.Tools;
import empmanager.alochol.empmanager.wedgit.XListView;
import okhttp3.Call;

/**
 * 用户列表页面
 */
public class ListUserActivity extends BasePageListActivity<Manager> {
    public static final String NAME = "NAME";
    public static final String MIN_AGE = "MIN_AGE";
    public static final String MAX_AGE = "MAX_AGE";
    public static final String GENDER = "GENDER";
    @BindView(R.id.imgTitleLeft)
    ImageView imgTitleLeft;
    @BindView(R.id.tvTitleCenter)
    TextView tvTitleCenter;
    @BindView(R.id.tvTitleRight)
    TextView tvTitleRight;
    @BindView(R.id.list_record)
    XListView listRecord;
    @BindView(R.id.iv_NoRecords)
    ImageView ivNoRecords;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Context context;

    private String mName;
    private String mMinAge;
    private String mMaxAge;
    private String mGender;

    @OnClick({R.id.imgTitleLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgTitleLeft:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        tvTitleCenter.setText("用户列表");
    }

    @Override
    protected XListView getListView() {
        return this.listRecord;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        context = this;
        Intent intent = getIntent();
        mName = intent.getStringExtra(NAME);
        mMinAge = intent.getStringExtra(MIN_AGE);
        mMaxAge = intent.getStringExtra(MAX_AGE);
        mGender = intent.getStringExtra(GENDER);
        super.initData(savedInstanceState);
//        obtainUserList(mName, mMinAge, mMaxAge, mGender);
    }

    @Override
    protected void loadByPage(int page) {
        obtainUserList(mName, mMinAge, mMaxAge, mGender, page);
    }

    private void obtainUserList(String name, String minAge, String maxAge, String gender, int page) {
        Map<String, String> map = new HashMap<>();
        if (name != null && !TextUtils.isEmpty(name.trim()))
            map.put("mgrName", name);
        if (minAge != null && !TextUtils.isEmpty(minAge.trim()))
            map.put("ageStart", minAge);
        if (maxAge != null && !TextUtils.isEmpty(maxAge.trim()))
            map.put("ageEnd", maxAge);
        if (gender != null && !TextUtils.isEmpty(gender.trim()))
            map.put("gender", gender);

        OkHttpUtils
                .get()
                .url(Constants.URL_MANAGER_BY_PAGE + "?page=" + page)
                .params(map)
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
                        PageInfo pageInfo = GsonUtil.GsonToBean(GsonUtil.GsonString(serviceResult.getResult()), PageInfo.class);
                        List<Manager> managers = GsonUtil.jsonToList(GsonUtil.GsonString(pageInfo.getList()), Manager.class);
                        pageInfo.setList(managers);
                        serviceResult.setResult(pageInfo);
                        processPageResp(context, serviceResult);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }
}
