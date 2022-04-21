package com.example.codefellowship.web;

import com.example.codefellowship.domain.ApplicationUser;
import com.example.codefellowship.infrastructure.AppUserRepo;
import com.example.codefellowship.infrastructure.PostRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AppUserController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AppUserRepo appUserRepo;
    @Autowired
    PostRepo postRepo;

    @GetMapping("/signin")
    public String getLoginPage(){
        return "signin";
    }

    @GetMapping("/signup")
    public String getSignupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignupUser(@RequestParam String username, @RequestParam String password ,@RequestParam String firstName,@RequestParam String lastName,@RequestParam String dateOfBirth,@RequestParam String bio){
        ApplicationUser appUser = new ApplicationUser(username,encoder.encode(password),firstName,lastName,dateOfBirth,bio);
        appUserRepo.save(appUser);
        return "signin.html";
    }

    @GetMapping("/myprofile")
    public String getProfile(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("newPosts", appUserRepo.findByUsername(userDetails.getUsername()).getPosts());
        return "profile.html";
    }

    @GetMapping("/signOut")
    public RedirectView signOut()
    {
        return new RedirectView("/");
    }

    @ModelAttribute("user")
    public ApplicationUser myUserEverywhere(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == ApplicationUser.class)
        {
            ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user;
        }
        else
            return null;
    }
    @GetMapping("/error")
    public String customError() {
        return "error.html";
    }

}
