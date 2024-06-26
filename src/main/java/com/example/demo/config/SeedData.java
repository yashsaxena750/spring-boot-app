package com.example.demo.config;

import com.example.demo.Controller.AccountController;
import com.example.demo.logging.LogMessages;
import com.example.demo.models.Post;
import com.example.demo.repositories.PostRepository;
import com.example.demo.services.PostService;
import com.example.demo.util.constants.Privileges;
import com.example.demo.util.constants.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.models.Account;
import com.example.demo.models.Authority;
import com.example.demo.services.AccountService;
import com.example.demo.services.AuthorityService;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;

@Component
public class SeedData implements CommandLineRunner {
    String configFilePath = "D:\\springboot\\final\\src\\main\\resources\\config.properties";
    Properties prop = new Properties();

    private static final Logger logger = LoggerFactory.getLogger(SeedData.class);

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {

        for(Privileges priv: Privileges.values())
        {
            Authority authority = new Authority();
            authority.setId(priv.getId());
            authority.setName(priv.getPrivilege());
            authorityService.save(authority);
        }

        Account account01 = new Account();
        Account account02 = new Account();
        Account account03 = new Account();

        try (FileInputStream propsInput = new FileInputStream(configFilePath)) {
        {
            prop.load(propsInput);
        // Check if accounts already exist
            if (accountService.findByEmail("yash@pug.com") == null) {

                account01.setEmail("yash@pug.com");
                account01.setPassword(prop.getProperty("yash_pug"));
                account01.setFirstname("user01");
                account01.setRole(Roles.USER.getRole());
                accountService.save(account01);
                logger.info(LogMessages.DATA_SAVED.getMessage());
            }

            if (accountService.findByEmail("gullu@pug.com") == null) {

                account02.setEmail("gullu@pug.com");
                account02.setPassword(prop.getProperty("gullu_pug"));
                account02.setFirstname("user02");
                account02.setRole(Roles.USER.getRole());
                accountService.save(account02);
                logger.info(LogMessages.DATA_SAVED.getMessage());
            }

            if(accountService.findByEmail("admin@pug.com")==null)
            {

                account03.setEmail("admin@pug.com");
                account03.setPassword(prop.getProperty("admin_gullu"));
                account03.setFirstname("admin");
                account03.setRole(Roles.ADMIN.getRole());
                Set<Authority> authorities = new HashSet<>();
                authorityService.findById(Privileges.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
                authorityService.findById(Privileges.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
                account03.setAuthorities(authorities);
                accountService.save(account03);

            }
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

            Post post03 = new Post();
            post03.setTitle("Admin Post");
            post03.setBody("Hello I am puggy the admin of this website, here you will find out more info about pugs");
            post03.setAccount(account03);
            postService.save(post03);

            logger.info(LogMessages.POST_ADDED.getMessage());
        }
    }
}
}
