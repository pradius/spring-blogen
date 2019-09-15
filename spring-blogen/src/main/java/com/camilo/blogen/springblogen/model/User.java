package com.camilo.blogen.springblogen.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="num_posts")
    private int numPosts;
    @Column(name="bio")
    private String bio;

    public User(){}

    public User(String username) {
        this.username = username;

    }
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public int getNumPosts() {return numPosts;}

    public void setNumPosts(int numPosts) {this.numPosts = numPosts;}

    public String getBio() {return bio;}

    public void setBio(String bio) {this.bio = bio;}

}
