package com.docker.spring_boot.service;

import com.docker.spring_boot.domain.Customer;
import com.docker.spring_boot.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       Customer user = userService.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }else {
            return new User(email, user.getPassword(),
                    new ArrayList<>());
        }
    }
}
