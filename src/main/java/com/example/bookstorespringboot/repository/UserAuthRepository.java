package com.example.bookstorespringboot.repository;
import com.example.bookstorespringboot.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {
    Optional<UserAuth> findByEmail(String email);

    @Query("SELECT ua.type FROM UserAuth ua WHERE ua.email = :email")
    Integer findTypeByEmail(@Param("email") String email);

    @Query("SELECT ua.disabled FROM UserAuth ua WHERE ua.email = :email")
    Boolean findDisabledByEmail(@Param("email") String email);
}
