package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.model.Users;
import com.example.bookstorespringboot.repository.UserAuthRepository;
import com.example.bookstorespringboot.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new User(userAuth.getEmail(), userAuth.getPassword(), Collections.emptyList());
    }


    public UserAuth registerUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        // 创建UserAuth实体
        UserAuth newUserAuth = new UserAuth(email, encodedPassword);
        userAuthRepository.save(newUserAuth);

        // 创建User实体
        Users newUser = new Users();
        newUser.setEmail(email);  // 假设User也有email字段
        newUser.setName(email);
        newUser.setId(newUserAuth.getId());
        newUser.setAddress("");
        newUser.setTelephone("");
        newUser.setType(0);
        newUser = userRepository.save(newUser);


        System.out.println(password);
        return newUserAuth;
    }


}
