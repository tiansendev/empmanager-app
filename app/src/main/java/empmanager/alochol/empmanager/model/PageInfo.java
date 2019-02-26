package empmanager.alochol.empmanager.model;

import java.util.List;

/**
 * 分页信息封装
 * @param <T>
 */
public class PageInfo<T> {
    private List<T> list;
    private int total;
    private int size;
    private boolean hasNextPage;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
