package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.model.Users;
import com.example.bookstorespringboot.repository.UserAuthRepository;
import com.example.bookstorespringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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

        // 获取用户的角色
        String role = "ROLE_USER"; // 默认角色
        if (userAuth.getType() == 1) {
            role = "ROLE_ADMIN";
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        return new User(userAuth.getEmail(), userAuth.getPassword(), authorities);
    }

    public UserAuth registerUser(String email, String password, String address, String name, String telephone, int type) {
        String encodedPassword = passwordEncoder.encode(password);

        // 创建 UserAuth 实体
        UserAuth newUserAuth = new UserAuth(email, encodedPassword);
        newUserAuth.setType(type);
        newUserAuth.setDisabled(false);
        userAuthRepository.save(newUserAuth);

        // 创建 User 实体
        Users newUser = new Users();
        newUser.setEmail(email);  // 假设 User 也有 email 字段
        newUser.setName(name);
        newUser.setId(newUserAuth.getId());
        newUser.setAddress(address);
        newUser.setTelephone(telephone);
        newUser.setType(type);
        userRepository.save(newUser);

//        System.out.println(password);
        return newUserAuth;
    }

    public Integer getIdByEmail(String email) {
        Optional<UserAuth> userAuth = userAuthRepository.findByEmail(email);
        if (userAuth.isPresent()) {
            return userAuth.get().getId();
        } else {
            throw new RuntimeException("No user found with the given email: " + email);
        }
    }
}
