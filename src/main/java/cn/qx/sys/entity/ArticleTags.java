package cn.qx.sys.entity;

import java.io.Serializable;

/***
 * 
 * @author Satone
 * @date 2019年2月21日
 */
public class ArticleTags implements Serializable {

    private static final long serialVersionUID = 2031879618788599052L;
    private long id; // 编号
    private long articleId; // 文章ID
    private long tagsId; // 标签ID

    public ArticleTags() {
    }

    public ArticleTags(long articleId, long tagsId) {
        this.articleId = articleId;
        this.tagsId = tagsId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getTagsId() {
        return tagsId;
    }

    public void setTagsId(long tagsId) {
        this.tagsId = tagsId;
    }

    @Override
    public String toString() {
        return "ArticleTags{" + "id=" + id + ", articleId=" + articleId + ", tagsId=" + tagsId + '}';
    }
}
