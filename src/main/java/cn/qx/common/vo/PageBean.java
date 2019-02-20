package cn.qx.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 用于封装页面bean对象
 * @author Satone
 * @date 2019年2月20日下午8:52:19
 * @param <T>页面值对象
 */
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = 7740594774191916702L;
    // 当前页
    private long total;
    // 当前页记录
    private List<T> rows;

    public PageBean(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
