package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;


@Controller
public class AdminController {

    @Autowired AccountRepository accountRepository;

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        List<Account> users = accountRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }
    
}
