package cn.qx.common.vo;


import java.io.Serializable;
import java.util.List;

import cn.qx.sys.entity.Comments;

/**
 * 封装评论消息
 * @author Satone
 * @date 2019年2月21日
 */
public class CommentsDTO implements Serializable {

    private static final long serialVersionUID = 1709850223750518867L;
    private Comments parent; //父级留言信息
    private List<Comments> childrenList; //所有子级回复、评论列表

    public CommentsDTO() {
    }

    public CommentsDTO(Comments parent, List<Comments> childrenList) {
        this.parent = parent;
        this.childrenList = childrenList;
    }

    public Comments getParent() {
        return parent;
    }

    public void setParent(Comments parent) {
        this.parent = parent;
    }

    public List<Comments> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Comments> childrenList) {
        this.childrenList = childrenList;
    }

    @Override
    public String toString() {
        return "CommentsDTO{" +
                "parent=" + parent +
                ", childrenList=" + childrenList +
                '}';
    }
}
