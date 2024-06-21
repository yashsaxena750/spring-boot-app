package com.example.demo.Controller;

import com.example.demo.models.Account;
import com.example.demo.services.AccountService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import com.example.demo.logging.LogMessages;
import com.example.demo.models.Post;
import com.example.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new Account());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute Account account, Model model) {
        try {
            accountService.save(account);
            logger.info(LogMessages.ACC_CREATED.getMessage()+" for: "+account.getEmail());
            model.addAttribute("msg", "Register successful");
            return "register";
        } catch (Exception e) {
            logger.info(LogMessages.ACC_FA_CREATED.getMessage()+" for: "+account.getEmail());
            model.addAttribute("error", "Something went wrong");
            return "register";
        }
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

}
