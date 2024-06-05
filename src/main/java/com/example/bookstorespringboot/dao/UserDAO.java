package com.example.bookstorespringboot.dao;

import com.example.bookstorespringboot.model.Users;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Users save(Users user);

    Optional<Users> findByName(String name);

    List<Users> findAll();

    Optional<Users> findById(Long id);

    void deleteById(Long id);
}
