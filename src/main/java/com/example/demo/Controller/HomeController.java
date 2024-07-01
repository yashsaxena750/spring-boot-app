package com.example.demo.Controller;

import com.example.demo.logging.LogMessages;
import com.example.demo.models.Post;
import com.example.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String root(Model model) {

        try {
            List<Post> posts = postService.getAll();
            if (!posts.isEmpty()) {
                model.addAttribute("posts", posts);
                logger.info(LogMessages.HOME_PAGE_HIT.getMessage());
            } else {
                logger.info("[INFO] No Posts found");
                model.addAttribute("posts", Collections.emptyList());
                model.addAttribute("message", "No Post to show currently");
            }
        } catch (Exception e) {
            logger.error(LogMessages.GENERAL_ERROR.getMessage(), e);
            model.addAttribute("posts", Collections.emptyList());
            model.addAttribute("message", "Something went wrong.");
        }
        return "home";

    }
}
