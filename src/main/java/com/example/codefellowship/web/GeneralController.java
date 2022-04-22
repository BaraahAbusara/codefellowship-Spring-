package com.example.codefellowship.web;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.domain.Post;
import com.example.codefellowship.infrastructure.AppUserRepo;
import com.example.codefellowship.infrastructure.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class GeneralController {
    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    PostRepo postRepo;

    @GetMapping("/")
    public String getHomePage(){
        return "index.html";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        model.addAttribute("usersList", appUserRepo.findAll());
        return "users.html";
    }

    @GetMapping("/users/{id}")
    String viewAlbum (@PathVariable Integer id , Model model) {
        ApplicationUser thisUser = appUserRepo.findById(id).orElseThrow();
        //attach posts
        model.addAttribute("posts",thisUser.getPosts());
        model.addAttribute("thisUser",thisUser);

        return "userDetails.html";
    }


    @PostMapping("/myprofile/{id}/addpost")
    public RedirectView addPost( @RequestParam String postBody , @PathVariable Integer id , Model model){
        //create post
        Post post = new Post(postBody, LocalDateTime.now().toString());
        //find related user
        ApplicationUser thisUser = appUserRepo.findById(id).orElseThrow();
        //set the author
        post.setAuthor(thisUser);
        //update posts
        List<Post> newPosts = thisUser.getPosts();
        newPosts.add(post);
        thisUser.setPosts(newPosts);
        //save the post
        postRepo.save(post);
        //attach posts
        model.addAttribute("newPosts",newPosts);
        return new RedirectView ("/myprofile");
    }

    @GetMapping("/feed")
    public String getFeed (Model model){
        ApplicationUser userNow = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userNow",userNow);
        List <Post> posts = postRepo.findAllByAuthor(userNow.getUsername());
        model.addAttribute("posts",posts);

        return "feed.html";
    }

//    private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.addDialect(new Java8TimeDialect());
//        engine.setTemplateResolver(templateResolver);
//        return engine;
//    }


}
