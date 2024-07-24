package com.sqa.domain.models;

import java.util.Objects;

/**
 * The Post DTO to serialize and deserialize
 */
public class Post {

    private int userId;
    private int id;
    private String title;
    private String body;

    // Default constructor
    public Post() {
    }

    // Parametrized constructor. Does not set record id
    public Post(int userId, String title, String body){
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    // Parametrized constructor. Does set record id
    public Post(int id, int userId, String title, String body){
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (userId != (post.userId)) return false;
        if (!Objects.equals(title, post.title)) return false;
        return Objects.equals(body, post.body);
    }

    @Override
    public int hashCode(){
        int result = userId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
