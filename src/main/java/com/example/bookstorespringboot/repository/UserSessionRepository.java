package com.example.bookstorespringboot.repository;

import com.example.bookstorespringboot.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    UserSession findByUserId(String email);

    void deleteByUserId(String email);
}
