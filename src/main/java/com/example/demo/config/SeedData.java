package com.example.demo.config;

import com.example.demo.models.Post;
import com.example.demo.repositories.PostRepository;
import com.example.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.models.Account;
import com.example.demo.services.AccountService;
import java.util.List;
import java.util.ArrayList;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {

        Account account01 = new Account();
        Account account02 = new Account();
        // Check if accounts already exist
        if (accountService.findByEmail("yash@pug.com") == null) {

            account01.setEmail("yash@pug.com");
            account01.setPassword("password");
            account01.setFirstname("user01");
            accountService.save(account01);
        }

        if (accountService.findByEmail("gullu@pug.com") == null) {

            account02.setEmail("gullu@pug.com");
            account02.setPassword("password");
            account02.setFirstname("user02");
            accountService.save(account02);
        }

        // Check if posts already exist
        List<Post> existingPosts = postService.getAll();
        if (existingPosts.isEmpty()) {
            Post post01 = new Post();
            post01.setTitle("Post 01");
            post01.setBody("This is a post 01");
            post01.setAccount(account01);
            postService.save(post01);

            Post post02 = new Post();
            post02.setTitle("Post 02");
            post02.setBody("This is a post 02");
            post02.setAccount(account02);
            postService.save(post02);
        }
    }
}
