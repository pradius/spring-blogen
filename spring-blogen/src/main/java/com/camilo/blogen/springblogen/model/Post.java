package com.camilo.blogen.springblogen.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
    @Column
    private String category;
    @Column
    private String content;
    @Column
    private String date;
    @Column(name = "created_by")
    private String createdBy;

    public Post() {}
    public Post(String title, String category, String content) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        this.title = title;
        this.category = category;
        this.content = content;
        this.date = dtf.format(now);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        this.date = dtf.format(now);
    }

    public String getContent() {return content; }

    public void setContent(String content) { this.content = content; }

    public String getCreatedBy() {return createdBy;}

    public void setCreatedBy(String user) {this.createdBy = user;}
}
