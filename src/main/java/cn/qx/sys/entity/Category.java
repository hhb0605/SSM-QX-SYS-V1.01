package cn.qx.sys.entity;

import java.io.Serializable;

/**
 * 
 * @author Satone
 * @date 2019年2月20日
 */
public class Category implements Serializable {

    private static final long serialVersionUID = -5624251619033083822L;
    private long id; // 编号
    private String name; // 分类名称

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
