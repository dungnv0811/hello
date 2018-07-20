package com.example.hello.repository.user;

import com.example.hello.domain.FootballOAuth2AccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FootballOAuth2AccessTokenRepository extends MongoRepository<FootballOAuth2AccessToken, String> {
    FootballOAuth2AccessToken findByTokenId(String tokenId);

    boolean deleteByTokenId(String tokenId);

    boolean deleteByRefreshTokenId(String refreshTokenId);

    FootballOAuth2AccessToken findByAuthenticationId(String key);

    List<FootballOAuth2AccessToken> findByUsernameAndClientId(String username, String clientId);

    List<FootballOAuth2AccessToken> findByClientId(String clientId);
}
