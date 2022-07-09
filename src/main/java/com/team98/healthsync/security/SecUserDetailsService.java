package com.team98.healthsync.security;


import com.team98.healthsync.models.Account;
import com.team98.healthsync.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;




@Service
@Transactional
public class SecUserDetailsService implements UserDetailsService {


    @Autowired
    private AccountRepository accountRepository;

    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(account.getRole().toString()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

}