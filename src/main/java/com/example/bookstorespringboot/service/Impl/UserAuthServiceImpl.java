package com.example.bookstorespringboot.service.Impl;

import com.example.bookstorespringboot.dao.UserAuthDAO;
import com.example.bookstorespringboot.dao.UserDAO;
import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.model.Users;
import com.example.bookstorespringboot.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthDAO userAuthDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String role = "ROLE_USER"; // 默认角色
        if (userAuth.getType() == 1) {
            role = "ROLE_ADMIN";
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        return new User(userAuth.getEmail(), userAuth.getPassword(), authorities);
    }

    @Override
    public UserAuth registerUser(String email, String password, String address, String name, String telephone, int type) {
        String encodedPassword = passwordEncoder.encode(password);

        UserAuth newUserAuth = new UserAuth(email, encodedPassword);
        newUserAuth.setType(type);
        newUserAuth.setDisabled(false);
        userAuthDAO.save(newUserAuth);

        Users newUser = new Users();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setId(newUserAuth.getId());
        newUser.setAddress(address);
        newUser.setTelephone(telephone);
        newUser.setType(type);
        userDAO.save(newUser);

        return newUserAuth;
    }

    @Override
    public Integer getIdByEmail(String email) {
        Optional<UserAuth> userAuth = userAuthDAO.findByEmail(email);
        if (userAuth.isPresent()) {
            return userAuth.get().getId();
        } else {
            throw new RuntimeException("No user found with the given email: " + email);
        }
    }

    @Override
    public UserAuth disableUser(Integer userId) {
        UserAuth userAuth = userAuthDAO.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userAuth.setDisabled(true);
        return userAuthDAO.save(userAuth);
    }

    @Override
    public UserAuth enableUser(Integer userId) {
        UserAuth userAuth = userAuthDAO.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userAuth.setDisabled(false);
        return userAuthDAO.save(userAuth);
    }

    @Override
    public Page<UserAuth> getAllUsers(Pageable pageable) {
        return userAuthDAO.findAll(pageable);
    }

    @Override
    public Optional<UserAuth> findUserByEmail(String email) {
        return userAuthDAO.findByEmail(email);
    }

    @Override
    public Optional<Users> findUserByName(String name) {
        return userDAO.findByName(name);
    }
}
