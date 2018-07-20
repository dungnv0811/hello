package com.example.hello.repository.user;

import com.example.hello.domain.FootballOAuth2ClientToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FootballOAuth2ClientTokenRepository extends MongoRepository<FootballOAuth2ClientToken, String> {
    boolean deleteByAuthenticationId(String authenticationId);

    FootballOAuth2ClientToken findByAuthenticationId(String authenticationId);
}
