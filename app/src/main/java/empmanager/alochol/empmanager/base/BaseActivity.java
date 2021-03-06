package empmanager.alochol.empmanager.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Activity 基类，抽出共用方法
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mPd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData(savedInstanceState);
        BaseAppliction.sActivityStack.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseAppliction.sActivityStack.remove(BaseAppliction.sActivityStack.indexOf(this));
    }

    protected void initView() {}

    protected abstract void initData(Bundle savedInstanceState);

    public abstract int getLayoutId();

    public void hideLoading() {
        if (mPd != null && mPd.isShowing())
            mPd.dismiss();
    }

    /**
     * 显示进度条 自定义信息
     * @param msg
     */
    protected void showLoading(String msg) {
        if (mPd == null) {
            mPd = new ProgressDialog(this);
            mPd.setCancelable(true);
            mPd.setCanceledOnTouchOutside(false);
        }
        if (!mPd.isShowing()) {
            mPd.setMessage(msg);
            mPd.show();
        }
    }

    /**
     * 显示进度条 默认信息
     */
    public void showLoading() {
        if (mPd == null) {
            mPd = new ProgressDialog(this);
            mPd.setMessage("正在加载...");
            mPd.setCanceledOnTouchOutside(false);
            mPd.setCancelable(true);
        }
        if (!mPd.isShowing())
            mPd.show();
    }


    /**
     * 对话框点击事件
     */
    public abstract static class OnClickEvent {
        public abstract void onPositive(View v);
        public void onNegative(){}
    }


    /**
     * 显示对话框
     * @param title
     * @param msg
     * @param event
     */
    public void showDialog(String title, String msg, final OnClickEvent event) {
        final MaterialDialog materialDialog = new MaterialDialog(this);

        materialDialog.setTitle(title)
                .setMessage(msg)
                .setCanceledOnTouchOutside(true);

        materialDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
                event.onPositive(v);
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    /**
     * 显示对话框 自定义布局
     * @param title
     * @param v
     * @param event
     */
    protected void showDialog(String title, View v, final OnClickEvent event) {
        final MaterialDialog materialDialog = new MaterialDialog(this);

        materialDialog.setTitle(title)
                .setContentView(v)
                .setCanceledOnTouchOutside(true);

        materialDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
                event.onPositive(v);
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

}
