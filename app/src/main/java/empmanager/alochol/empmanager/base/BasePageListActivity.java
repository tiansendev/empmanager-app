package empmanager.alochol.empmanager.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import empmanager.alochol.empmanager.adapter.FilterListAdapter;
import empmanager.alochol.empmanager.model.PageInfo;
import empmanager.alochol.empmanager.model.ServiceResult;
import empmanager.alochol.empmanager.model.ServiceResultCode;
import empmanager.alochol.empmanager.util.Tools;
import empmanager.alochol.empmanager.wedgit.XListView;

public abstract class BasePageListActivity<T> extends BaseActivity implements XListView.IXListViewListener, AbsListView.OnScrollListener {
    protected int page;
    protected List<T> list;
    protected BaseAdapter adapter;
    private boolean mIsNoMoreData;
    protected abstract XListView getListView();
    @Override
    protected void initData(Bundle savedInstanceState) {
        if (getListView() == null)
            return;

        getListView().setXListViewListener(this);
        getListView().setOnScrollListener(this);
        getListView().setPullLoadEnable(false);
        list = new ArrayList<>();
        adapter = new FilterListAdapter(this, list);
        getListView().setAdapter(adapter);
        loadByPage(++page);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        switch (i) {
            case SCROLL_STATE_IDLE:
                boolean flag = isListViewReachBottomEdge(absListView);
                if (flag && mIsNoMoreData)
                    Tools.showInfoShort(this, "已无更多");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {}

    public boolean isListViewReachBottomEdge(final AbsListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }

    @Override
    public void onLoadMore() {
        loadByPage(page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadByPage(page);
    }

    private void onLoadComplete() {
        getListView().stopRefresh();//停止刷新
        getListView().stopLoadMore();//停止加载更多
    }

    protected abstract void loadByPage(int page);

    protected void processPageResp(Context context, ServiceResult<PageInfo<T>> serviceResult) {
        if (getListView() == null)
            return;
        onLoadComplete();

        List<T> infos = null;
        PageInfo<T> pageInfo = null;
        if (ServiceResultCode.SUCCESS.getCode().equals(serviceResult.getCode())) {
            if (serviceResult.getResult() != null && serviceResult.getResult().getList() != null) {
                pageInfo = serviceResult.getResult();
                infos = pageInfo.getList();
            }
            else {
                Tools.showInfoLong(context, "暂无信息");
                getListView().setPullLoadEnable(false);
                return;
            }
        } else {
            Tools.showInfoLong(context, "获取信息失败：" + serviceResult.getMessage());
            return;
        }

        if (infos == null || infos.size() == 0) {
            Tools.showInfoShort(context, "无相关信息");
            return;
        }

        if (page == 1) {
            // 第一次加载或搜索
            list.clear();
        }

        list.addAll(infos);
        adapter.notifyDataSetChanged();

        // 全部加载完了
        if (!pageInfo.isHasNextPage()) {
            mIsNoMoreData = true;
            getListView().setPullLoadEnable(false);
        } else {
            mIsNoMoreData = false;
            if (!getListView().getPullLoadEnable())
                getListView().setPullLoadEnable(true);
            page++;
        }
    }
}