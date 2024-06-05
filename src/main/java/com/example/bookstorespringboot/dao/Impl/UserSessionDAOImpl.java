package com.example.bookstorespringboot.dao.Impl;

import com.example.bookstorespringboot.dao.UserSessionDAO;
import com.example.bookstorespringboot.model.UserSession;
import com.example.bookstorespringboot.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserSessionDAOImpl implements UserSessionDAO {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Override
    public void save(UserSession session) {
        userSessionRepository.save(session);
    }

    @Override
    public void deleteByUserId(String userId) {
        userSessionRepository.deleteByUserId(userId);
    }
}
