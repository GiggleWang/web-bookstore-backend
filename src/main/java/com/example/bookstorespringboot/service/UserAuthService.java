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
import java.util.Optional;

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


    public UserAuth registerUser(String email, String password,String address,String name ,String telephone , int type) {
        String encodedPassword = passwordEncoder.encode(password);
        // 创建UserAuth实体
        UserAuth newUserAuth = new UserAuth(email, encodedPassword);
        userAuthRepository.save(newUserAuth);

        // 创建User实体
        Users newUser = new Users();
        newUser.setEmail(email);  // 假设User也有email字段
        newUser.setName(name);
        newUser.setId(newUserAuth.getId());
        newUser.setAddress(address);
        newUser.setTelephone(telephone);
        newUser.setType(type);
        newUser = userRepository.save(newUser);


        System.out.println(password);
        return newUserAuth;
    }

    public Integer getIdByEmail(String email) {
        Optional<UserAuth> userAuth = userAuthRepository.findByEmail(email);
        if(userAuth.isPresent()){
            return userAuth.get().getId();
        }else {
            throw new RuntimeException("No user found with the given email: " + email);
        }
    }
}
