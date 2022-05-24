package com.ssafy.happyhouse.domain.entity;

public class Comment {

    private Long id;
    private Long article_id;
    private String content;

    public Comment() { }

    public Comment(Long id, Long article_id, String content) {
        this.id = id;
        this.article_id = article_id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
