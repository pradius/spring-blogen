package com.camilo.blogen.springblogen.controller;

import com.camilo.blogen.springblogen.model.Post;
import com.camilo.blogen.springblogen.model.User;
import com.camilo.blogen.springblogen.service.PostService;
import com.camilo.blogen.springblogen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService users;
    @Autowired
    PostService posts;

    @GetMapping("/login")
    public String login(HttpServletRequest req) {
        req.getSession().invalidate();
        return "login";
    }

    @PostMapping("/logged")
    public String logged(RedirectAttributes model, @RequestParam String username, @RequestParam String password, HttpServletRequest req) {

        List<User> userList = users.getAllUsers();
        for (User user : userList) {
            if (user.getUsername().equals(username) && BCrypt.checkpw(password, user.getPassword())) {
                // Set up session
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                return "redirect:/dashboard";
            }
        }
        String msg = "Your username or password are incorrect, please try again";
        model.addFlashAttribute("msg", msg);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/registered")
    public String registered(RedirectAttributes model, @RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String cpassword) {
        List<User> userList = users.getAllUsers();
        String msg = "";
        for (User user : userList) {
            if (user.getEmail().equals(email) || user.getUsername().equals(username)) {
                msg = "The email or username already exits. Please ";
                model.addFlashAttribute("msg", msg);
                return "redirect:/register";
            }
        }
        User user = new User();
        if (password.equals(cpassword)) {
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            users.addUser(user);
            msg = "Account created";
            model.addFlashAttribute("msg", msg);
            return "redirect:/login";
        } else {
            msg = "Your password does not match";
            model.addFlashAttribute("message", msg);
            return "register";
        }

    }

    @GetMapping("/users")
    public String users(Model model, HttpServletRequest req) {
        if (req.getSession().getAttribute("username") == null) {
            return "redirect:/login";
        }
        model.addAttribute("users", users.getAllUsers());
        return "users";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword, HttpServletRequest req) {
        if (req.getSession().getAttribute("username") == null) {
            return "redirect:/login";
        }

        boolean isPresent = false;
        for (User user : users.getAllUsers()) {
            if (user.getEmail().equals(email)) {
                isPresent = true;
            }
        }
        User user = new User();
        if (password.equals(confirmPassword) && !isPresent) {
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            users.addUser(user);
            return "redirect:/dashboard";
        } else {
            System.out.println("Something when wrong with this user");
            return "redirect:/users";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest req) {
        if (req.getSession().getAttribute("username") == null) {
            return "redirect:/login";
        }

        User user = users.getUser((String) req.getSession().getAttribute("username"));
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/password-update")
    public String updatePassword(RedirectAttributes redirect, HttpServletRequest req, @RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmPassword) {
        if (req.getSession().getAttribute("username") == null) {
            return "redirect:/login";
        }
        User user = users.getUser((String) req.getSession().getAttribute("username"));
        if (BCrypt.checkpw(currentPassword, user.getPassword())){
            if (newPassword.equals(confirmPassword)){
                user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
                users.addUser(user);
                String msg = "Password changed successfully";
                redirect.addFlashAttribute("msg", msg);
                return "redirect:/profile";
            }
        }
        String msg = "Make sure you enter a valid password";
        redirect.addFlashAttribute("msg", msg);
        return "redirect:/profile";
    }

    @PostMapping("/bio-update")
    public String bioUpdate(RedirectAttributes redirect, HttpServletRequest req, @RequestParam String editor){
        if (req.getSession().getAttribute("username") == null) {
            return "redirect:/login";
        }
        User thisUser = users.getUser((String) req.getSession().getAttribute("username"));
        thisUser.setBio(editor);
        users.addUser(thisUser);
        String msg = "Bio updated";
        redirect.addFlashAttribute("msg", msg);
        return "redirect:/profile";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(RedirectAttributes redirect, HttpServletRequest req, @RequestParam String phrase ){
        if (req.getSession().getAttribute("username") == null) {
            return "redirect:/login";
        }
        User thisUser = users.getUser((String) req.getSession().getAttribute("username"));
        List<Post> postList = posts.getAllPosts();
        if(phrase.equals("DELETE")){
            for(Post post : postList){
                if(post.getCreatedBy().equals(thisUser.getUsername())){
                    posts.deletePost(post);
                }
            }
            users.deleteUser(thisUser.getUsername());
            String msg = thisUser.getUsername() +  " Account deleted";
            redirect.addFlashAttribute("msg", msg);
            return "redirect:/login";
        }
        String msg = thisUser.getUsername() +  " Could not be deleted";
        redirect.addFlashAttribute("msg", msg);
        return "redirect:/profile";

    }

}