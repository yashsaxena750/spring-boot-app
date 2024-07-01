package com.example.demo.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.text.StringEscapeUtils;
import com.example.demo.services.AccountService;
import com.example.demo.services.PostService;
import com.example.demo.models.Account;
import com.example.demo.models.Post;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;
    
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

    @GetMapping("/post/add")
    public String addPost(Model model, Principal principal)
    {
        
        String authUser = "email";

        if(principal!=null)
        {
            authUser = principal.getName();
        }

        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if(optionalAccount.isPresent())
        {
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post", post);
            return "post_views/post_add";
        }
        else
        {
            return "redirect:/";
        }
         
    }

    @PostMapping("/post/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@ModelAttribute Post post, Principal principal)
    {
        String authUser = "email";
        if(principal!=null)
        {
            authUser = principal.getName();
        }
        if(post.getAccount().getEmail().compareToIgnoreCase(authUser)<0)
        {
            return "redirect:/?error";
        }

        post.setTitle(StringEscapeUtils.escapeHtml4(post.getTitle()));
        post.setBody(StringEscapeUtils.escapeHtml4(post.getBody()));

        postService.save(post);
        
        return "redirect:/posts/"+post.getId();

    }
    

}
