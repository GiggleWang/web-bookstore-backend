package com.example.bookstorespringboot.service;

import com.example.bookstorespringboot.model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Users> findAllUsers();

    Optional<Users> findUserById(Long id);

    Users saveUser(Users user);

    void deleteUser(Long id);
}
