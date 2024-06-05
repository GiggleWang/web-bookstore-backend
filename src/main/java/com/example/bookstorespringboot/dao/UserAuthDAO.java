package com.example.bookstorespringboot.dao;

import com.example.bookstorespringboot.model.UserAuth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserAuthDAO {
    Integer findTypeByEmail(String email);

    Boolean findDisabledByEmail(String email);

    Optional<UserAuth> findByEmail(String email);

    UserAuth save(UserAuth userAuth);

    Optional<UserAuth> findById(Integer id);

    Page<UserAuth> findAll(Pageable pageable);
}
