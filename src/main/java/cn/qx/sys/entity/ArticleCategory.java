package cn.qx.sys.entity;

import java.io.Serializable;

/**
 * 
 * @author Satone
 * @date 2019年2月20日下午10:04:55
 */
public class ArticleCategory implements Serializable {
    
    private static final long serialVersionUID = -3011856672653813924L;
    private long id; // 编号
    private long articleId; // 文章ID
    private long categoryId; // 分类ID

    public ArticleCategory() {
    }

    public ArticleCategory(long articleId, long categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ArticleCategory{" + "id=" + id + ", articleId=" + articleId + ", categoryId=" + categoryId + '}';
    }
}
