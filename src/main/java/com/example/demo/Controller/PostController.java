package com.example.demo.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.services.PostService;
import com.example.demo.models.Post;


@Controller
public class PostController {

    @Autowired
    private PostService postService;
    
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) 
    {
        Optional<Post> optionalpost = postService.getById(id);
        String authUser = "email";
        if(optionalpost.isPresent())
        {
            Post post = optionalpost.get();
            model.addAttribute("post", post);
            
            if(principal!=null)
            {
                authUser = principal.getName();
            }

            if(authUser.equals(post.getAccount().getEmail()))
            {
                model.addAttribute("isOwner", true);
            }
            else
            {
                model.addAttribute("isOwner", false);
            }

            return "post_views/post";
        }
        else
        {
            return "error";
        }
    }
    

}
