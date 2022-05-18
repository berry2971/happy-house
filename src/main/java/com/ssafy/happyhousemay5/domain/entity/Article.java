package com.ssafy.happyhousemay5.domain.entity;

public class Article {

    private Long id;
    private String board_name;
    private String title;
    private String author;
    private String password;
    private String write_time;
    private String content;

    public Article() { }

    public Article(Long id, String board_name, String title, String author, String password, String write_time, String content) {
        this.id = id;
        this.board_name = board_name;
        this.title = title;
        this.author = author;
        this.password = password;
        this.write_time = write_time;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoard_name() {
        return board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWrite_time() {
        return write_time;
    }

    public void setWrite_time(String write_time) {
        this.write_time = write_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
