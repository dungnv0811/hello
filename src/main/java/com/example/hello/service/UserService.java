package com.example.hello.service;

import com.example.hello.domain.User;

import java.util.List;

public interface UserService {
    User save(User user);
    List<User> findAll();
    void delete(long id);
}
