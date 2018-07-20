package com.example.hello.repository.user;

import com.example.hello.domain.FootballOAuth2RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FootballOAuth2RefreshTokenRepository extends MongoRepository<FootballOAuth2RefreshToken, String> {
    FootballOAuth2RefreshToken findByTokenId(String tokenId);

    boolean deleteByTokenId(String tokenId);
}
