package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new User(userAuth.getEmail(), userAuth.getPassword(), Collections.emptyList());
    }


    public UserAuth registerUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        UserAuth newUser = new UserAuth(email, encodedPassword);
        System.out.println(password);
        return userAuthRepository.save(newUser);
    }


}
