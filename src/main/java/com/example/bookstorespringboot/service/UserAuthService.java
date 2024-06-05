package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserAuthService extends UserDetailsService {
    UserAuth registerUser(String email, String password, String address, String name, String telephone, int type);

    Integer getIdByEmail(String email);

    UserAuth disableUser(Integer userId);

    UserAuth enableUser(Integer userId);

    Page<UserAuth> getAllUsers(Pageable pageable);

    Optional<UserAuth> findUserByEmail(String email);

    Optional<Users> findUserByName(String name);
}
