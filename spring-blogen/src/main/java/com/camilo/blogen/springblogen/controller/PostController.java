package com.camilo.blogen.springblogen.controller;

import com.camilo.blogen.springblogen.model.Post;
import com.camilo.blogen.springblogen.model.User;
import com.camilo.blogen.springblogen.service.CategoryService;
import com.camilo.blogen.springblogen.service.PostService;
import com.camilo.blogen.springblogen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class PostController {

    @Autowired
    private PostService posts;
    @Autowired
    private CategoryService categories;
    @Autowired
    private UserService users;

    @GetMapping("/posts")
    public String posts(Model model, HttpServletRequest req ){
        if (req.getSession().getAttribute("username") == null ){return "redirect:/login";}
        model.addAttribute("posts", posts.getAllPosts());
        model.addAttribute("categories", categories.getAllCategories());
        return "posts";
    }

    @GetMapping("/posts/details/{id}")
    public String details(Model model, @PathVariable Long id, HttpServletRequest req){
        if (req.getSession().getAttribute("username") == null ){return "redirect:/login";}
        model.addAttribute("post", posts.getPostById(id));
        model.addAttribute("categories", categories.getAllCategories());
        return "details";
    }

    @PostMapping("/posts/add")
    public String savePost(@RequestParam String title, @RequestParam String category, @RequestParam String editor, HttpServletRequest req){
        if (req.getSession().getAttribute("username") == null ){return "redirect:/login";}
        String currentUser = (String) req.getSession().getAttribute("username");
        for (User user : users.getAllUsers() ){
            if (user.getUsername().equals(currentUser)){
                user.setNumPosts(user.getNumPosts() + 1);
            }
        }
        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setCategory(category);
        newPost.setDate();
        newPost.setContent(editor);
        newPost.setCreatedBy(currentUser);
        posts.savePost(newPost);
        return "redirect:/posts";
    }

    @PostMapping("/posts/update")
    public String updatePost(@RequestParam Long id, @RequestParam String title, @RequestParam String category, @RequestParam String editor1, HttpServletRequest req){
        if (req.getSession().getAttribute("username") == null ){return "redirect:/login";}
        Post post = posts.getPostById(id);
        post.setTitle(title);
        post.setCategory(category);
        post.setContent(editor1);
        posts.savePost(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/delete")
    public String deletePost(@RequestParam Long id, HttpServletRequest req){
        if (req.getSession().getAttribute("username") == null ){return "redirect:/login";}
        String currentUser = (String) req.getSession().getAttribute("username");
        for (User user : users.getAllUsers() ){
            if (user.equals(currentUser)){
                user.setNumPosts(user.getNumPosts() - 1);
            }
        }
        Post delPost = posts.getPostById(id);
        posts.deletePost(delPost);
        return "redirect:/posts";
    }
}
