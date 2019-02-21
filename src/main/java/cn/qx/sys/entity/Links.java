package cn.qx.sys.entity;

import java.io.Serializable;

/**
 * 
 * @author Satone
 * @date 2019年2月21日
 */
public class Links implements Serializable {

    private static final long serialVersionUID = 2435964372427459212L;
    private long id; // 编号
    private String name; // 连接名称
    private String url; // 连接URL

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
