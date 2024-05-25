package com.example.bookstorespringboot.repository;

import com.example.bookstorespringboot.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByName(String name);

    Users getByEmail(String email);
}
