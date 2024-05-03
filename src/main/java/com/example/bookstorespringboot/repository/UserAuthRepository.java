package com.example.bookstorespringboot.repository;
import com.example.bookstorespringboot.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {
    Optional<UserAuth> findByEmail(String email);
}
