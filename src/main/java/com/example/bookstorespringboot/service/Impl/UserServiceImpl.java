package com.example.bookstorespringboot.service.Impl;

import com.example.bookstorespringboot.dao.UserDAO;
import com.example.bookstorespringboot.model.Users;
import com.example.bookstorespringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<Users> findAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public Optional<Users> findUserById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public Users saveUser(Users user) {
        return userDAO.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteById(id);
    }
}
