package com.example.demo.services;

import com.example.demo.Controller.HomeController;
import com.example.demo.logging.LogMessages;
import com.example.demo.models.Account;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.util.constants.Roles;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account save(Account account){
        account.setRole(Roles.USER.getRole());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findByEmail(email));
        if(!optionalAccount.isPresent())
        {
            logger.info(LogMessages.USER_NOT_FOUND.getMessage()+" for email: "+email);
            throw new UsernameNotFoundException("No account found");
        }

        Account account = optionalAccount.get();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request == null) {
            throw new IllegalStateException("HttpServletRequest not found");
        }

        // Log full request URL
        String requestURL = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        String fullURL = queryString == null ? requestURL : requestURL + "?" + queryString;
        logger.info("Full URL: " + fullURL);
        String errorParam = request.getParameter("error");

        if(errorParam == null) {
            logger.info(LogMessages.USER_LOGIN_SUCCESS.getMessage() + " for email: " + email);
            List<GrantedAuthority> grantedAuthority = new ArrayList<>();
            grantedAuthority.add(new SimpleGrantedAuthority("Allow"));
            return new User(account.getEmail(), account.getPassword(), grantedAuthority);
        }
        else
        {
            logger.info(LogMessages.USER_LOGIN_FAILURE.getMessage() + " for email: " + email);
            return null;
        }
    }
}
