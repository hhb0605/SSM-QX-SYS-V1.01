package cn.qx.common.vo;


import java.io.Serializable;
import java.util.List;

import cn.qx.sys.entity.Article;

/**
 * 用于封装Article表按时间归档的VO
 * @author Satone
 * @date 2019年2月20日下午9:01:11
 */
public class ArticleArchives implements Serializable {
    private static final long serialVersionUID = 8730146410385298682L;
    private String date;
    private List<Article> articles;

    public ArticleArchives() {
    }

    public ArticleArchives(String date, List<Article> articles) {
        this.date = date;
        this.articles = articles;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "ArticleArchives{" +
                "date='" + date + '\'' +
                ", articles=" + articles +
                '}';
    }
}
