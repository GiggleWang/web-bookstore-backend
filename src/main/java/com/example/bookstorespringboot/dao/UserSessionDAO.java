package com.example.bookstorespringboot.dao;

import com.example.bookstorespringboot.model.UserSession;

public interface UserSessionDAO {
    void save(UserSession session);

    void deleteByUserId(String userId);
}
