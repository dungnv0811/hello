package com.example.hello.repository.user;

import com.example.hello.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
}
