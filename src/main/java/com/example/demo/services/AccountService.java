package com.example.demo.services;

import com.example.demo.Controller.HomeController;
import com.example.demo.logging.LogMessages;
import com.example.demo.models.Account;
import com.example.demo.models.Authority;
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
import java.util.Set;

@Service
public class AccountService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account save(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (account.getRole() == null){
            account.setRole(Roles.USER.getRole());
        }
        return accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findOneByEmailIgnoreCase(email);
        if(!optionalAccount.isPresent()){
            logger.info("user not found");
            throw new UsernameNotFoundException("Account not found");

        }
        Account account = optionalAccount.get();
        logger.info(account.getEmail());
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority(account.getRole()));

        Set<Authority> authorities =  account.getAuthorities();
        for(Authority _auth: authorities){
            grantedAuthority.add(new SimpleGrantedAuthority(_auth.getName()));
        }

        return new User(account.getEmail(), account.getPassword(), grantedAuthority);
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Optional<Account> findOneByEmail(String authUser) {
        
        return accountRepository.findOneByEmailIgnoreCase(authUser);
    }
}
