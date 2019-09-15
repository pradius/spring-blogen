package com.camilo.blogen.springblogen.controller;

import com.camilo.blogen.springblogen.model.Category;
import com.camilo.blogen.springblogen.service.CategoryService;
import com.camilo.blogen.springblogen.service.PostService;
import com.camilo.blogen.springblogen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AppController {
    @Autowired
    private PostService posts;
    @Autowired
    private UserService users;
    @Autowired
    private CategoryService categories;

    @GetMapping("/")
    public String login(){
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest req){
        if (req.getSession().getAttribute("username") == null ){return "redirect:/login";}

        model.addAttribute("posts", posts);
        model.addAttribute("users", users);
        model.addAttribute("categories", categories);
        return "dashboard";
    }

    @GetMapping("/categories")
    public String categories(Model model, HttpServletRequest req){
        if (req.getSession().getAttribute("username") == null ){return "redirect:/login";}
        model.addAttribute("categories", categories.getAllCategories());
        return "categories";
    }

    @PostMapping("/categories/add")
    public String addCategory(@RequestParam String name,  HttpServletRequest req){
        if (req.getSession().getAttribute("username") == null ){return "redirect:/login";}
        Category cat = new Category(name);
        categories.addCategory(cat);
        return "redirect:/categories";
    }
}
