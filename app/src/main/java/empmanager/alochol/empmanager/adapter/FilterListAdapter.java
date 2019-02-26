package empmanager.alochol.empmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.OtherRequestBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import empmanager.alochol.empmanager.R;
import empmanager.alochol.empmanager.base.BaseActivity;
import empmanager.alochol.empmanager.common.constant.Constants;
import empmanager.alochol.empmanager.model.Employee;
import empmanager.alochol.empmanager.model.Manager;
import empmanager.alochol.empmanager.model.ServiceResult;
import empmanager.alochol.empmanager.ui.EmployeeEditActivity;
import empmanager.alochol.empmanager.ui.UserEditActivity;
import empmanager.alochol.empmanager.util.GsonUtil;
import empmanager.alochol.empmanager.util.Tools;
import okhttp3.Call;

public class FilterListAdapter<T> extends BaseAdapter {

    private List<T> itemBeans;
    private Context mContext;

    public FilterListAdapter(Context context, List<T> positions) {
        this.mContext = context;
        this.itemBeans = positions;
    }

    public int getCount() {
        return this.itemBeans.size();
    }

    public Object getItem(int paramInt) {
        return this.itemBeans.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int position, View contentView, ViewGroup paramViewGroup) {
        T t = itemBeans.get(position);
        ViewHolder holder = null;
        if (contentView == null) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_manager_item, null);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        if (isUser(itemBeans.get(position))) {
            // 位置
            Manager manager = (Manager) t;
            holder.tvName.setText(manager.getMgr_name());
            Integer gender = manager.getGender();
            if (gender != null) {
                if (gender == 0)
                    holder.tvGender.setText("男");
                else if (gender == 1)
                    holder.tvGender.setText("女");
            }
            holder.tvAge.setText(String.valueOf(manager.getMgr_age()));
        } else if (isEmp(itemBeans.get(position))) {
            Employee employee = (Employee) t;
            holder.tvName.setText(employee.getEmp_name());
            Integer emp_gender = employee.getEmp_gender();
            if (emp_gender != null) {
                if (emp_gender == 0)
                    holder.tvGender.setText("男");
                else if (emp_gender == 1)
                    holder.tvGender.setText("女");
            }
            holder.tvAge.setText(String.valueOf(employee.getEmp_age()));
        }

        setClickListener(contentView, position);
        return contentView;
    }

    private boolean isUser(T t) {
        return t instanceof Manager;
    }

    private boolean isEmp(T t) {
        return t instanceof Employee;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_gender)
        TextView tvGender;
        @BindView(R.id.positionItemRl)
        RelativeLayout positionItemRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void setClickListener(final View contentView, final int position) {
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (isEmp(itemBeans.get(position))) {
                    intent.setClass(mContext, EmployeeEditActivity.class);
                    if (itemBeans.get(position) != null) {
                        intent.putExtra(EmployeeEditActivity.EMP_ID, ((Employee) itemBeans.get(position)).getId());
                        mContext.startActivity(intent);
                    }
                } else if (isUser(itemBeans.get(position))) {
                    intent.setClass(mContext, UserEditActivity.class);
                    if (itemBeans.get(position) != null) {
                        intent.putExtra(UserEditActivity.MANANGER_ID, ((Manager) itemBeans.get(position)).getId());
                    }
                    mContext.startActivity(intent);
                }
            }
        });

        // 长按删除
        contentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((BaseActivity)mContext).showDialog("警告", "确定要删除该条记录？", new BaseActivity.OnClickEvent() {
                    @Override
                    public void onPositive(View v) {
                        OtherRequestBuilder delete = OkHttpUtils.delete();
                        if (isEmp(itemBeans.get(position))) {
                            // 是员工
                            delete.url(Constants.URL_EMPLOYEE + "/" + ((Employee) itemBeans.get(position)).getId());
                        } else if (isUser(itemBeans.get(position))) {
                            // 是用户
                            delete.url(Constants.URL_MANAGER + "/" + ((Manager) itemBeans.get(position)).getId());
                        }
                        delete.build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        e.printStackTrace();
                                        ((BaseActivity)mContext).hideLoading();
                                        Tools.showInfoShort(mContext, "删除失败: " + e.getMessage());
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        ((BaseActivity)mContext).hideLoading();
                                        ServiceResult serviceResult = GsonUtil.GsonToBean(response, ServiceResult.class);
                                        if (!serviceResult.isSuccess()) {
                                            Tools.showInfoShort(mContext, "删除失败: " + serviceResult.getMessage());
                                            return;
                                        }
                                        // 刷新界面
                                        itemBeans.remove(position);
                                        notifyDataSetChanged();
                                    }
                                });
                    }
                });
                return true;
            }
        });
    }

}
