package com.example.codefellowship.web;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.domain.Post;
import com.example.codefellowship.infrastructure.AppUserRepo;
import com.example.codefellowship.infrastructure.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

        model.addAttribute("thisUser",thisUser);
        model.addAttribute("posts",thisUser.getPosts());

        return "userDetails.html";
    }


    @PostMapping("/myprofile/{id}/addpost")
    public RedirectView addPost( @RequestParam String postBody , @PathVariable Integer id , Model model){
        Post post = new Post(postBody,"20");
        ApplicationUser thisUser = appUserRepo.findById(id).orElseThrow();
        post.setAuthor(thisUser);
        List<Post> newPosts = thisUser.getPosts();
        newPosts.add(post);
        thisUser.setPosts(newPosts);
        postRepo.save(post);

        model.addAttribute("newPosts",newPosts);
        return new RedirectView ("/myprofile");
    }


}
