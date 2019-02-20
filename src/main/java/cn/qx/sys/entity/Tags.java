package cn.qx.sys.entity;

import java.io.Serializable;

/**
 * 
 * @author Satone
 * @date 2019年2月20日下午9:43:26
 */
public class Tags implements Serializable {
    private static final long serialVersionUID = 8945986289188048171L;
    private long id; //编号
    private String name; //标签名称

    public Tags() {
    }

    public Tags(String name) {
        this.name = name;
    }

    public Tags(long id, String name) {
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

}
