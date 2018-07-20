package com.example.hello.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Objects;

@Document
public class FootballOAuth2AccessToken {

    @Id
    private String tokenId;
    private byte[] token;
    private String authenticationId;
    private String username;
    private String clientId;
    private byte[] authentication;
    private String refreshTokenId;

    public FootballOAuth2AccessToken() {
    }

    @PersistenceConstructor
    public FootballOAuth2AccessToken(final String tokenId,
                                  final byte[] token,
                                  final String authenticationId,
                                  final String username,
                                  final String clientId,
                                  final byte[] authentication,
                                  final String refreshTokenId) {
        this.tokenId = tokenId;
        this.token = token;
        this.authenticationId = authenticationId;
        this.username = username;
        this.clientId = clientId;
        this.authentication = authentication;
        this.refreshTokenId = refreshTokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public String getUsername() {
        return username;
    }

    public String getClientId() {
        return clientId;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public String getRefreshToken() {
        return refreshTokenId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, authenticationId, username, clientId, authentication, refreshTokenId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FootballOAuth2AccessToken other = (FootballOAuth2AccessToken) obj;
        return Objects.equals(this.token, other.token) && Objects.equals(this.authenticationId, other.authenticationId) && Objects.equals(this.username, other.username) && Objects.equals(this.clientId, other.clientId) && Objects.equals(this.authentication, other.authentication) && Objects.equals(this.refreshTokenId, other.refreshTokenId);
    }

    @Override
    public String toString() {
        return "FootballOAuth2AccessToken{" +
            "tokenId='" + tokenId + '\'' +
            ", token=" + Arrays.toString(token) +
            ", authenticationId='" + authenticationId + '\'' +
            ", username='" + username + '\'' +
            ", clientId='" + clientId + '\'' +
            ", authentication=" + Arrays.toString(authentication) +
            ", refreshTokenId='" + refreshTokenId + '\'' +
            '}';
    }
}