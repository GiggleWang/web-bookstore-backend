package com.example.bookstorespringboot.dao.Impl;

import com.example.bookstorespringboot.dao.UserAuthDAO;
import com.example.bookstorespringboot.model.UserAuth;
import com.example.bookstorespringboot.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserAuthDAOImpl implements UserAuthDAO {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public UserAuthDAOImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public Integer findTypeByEmail(String email) {
        return userAuthRepository.findTypeByEmail(email);
    }

    @Override
    public Boolean findDisabledByEmail(String email) {
        return userAuthRepository.findDisabledByEmail(email);
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return userAuthRepository.findByEmail(email);
    }

    @Override
    public UserAuth save(UserAuth userAuth) {
        return userAuthRepository.save(userAuth);
    }

    @Override
    public Optional<UserAuth> findById(Integer id) {
        return userAuthRepository.findById(id);
    }

    @Override
    public Page<UserAuth> findAll(Pageable pageable) {
        return userAuthRepository.findAll(pageable);
    }
}
