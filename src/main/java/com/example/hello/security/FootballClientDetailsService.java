package com.example.hello.security;

import com.example.hello.domain.FootballClientDetails;
import com.example.hello.repository.user.FootballClientDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

@Component
public class FootballClientDetailsService implements ClientDetailsService, ClientRegistrationService {

    private final FootballClientDetailsRepository footballClientDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    public FootballClientDetailsService(final FootballClientDetailsRepository footballClientDetailsRepository,
                                     final PasswordEncoder passwordEncoder) {
        this.footballClientDetailsRepository = footballClientDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClientDetails loadClientByClientId(final String clientId) {
        try {
            return footballClientDetailsRepository.findByClientId(clientId);
        } catch (IllegalArgumentException e) {
            throw new ClientRegistrationException("No Client Details for client id", e);
        }
    }

    @Override
    public void addClientDetails(final ClientDetails clientDetails) {
        final FootballClientDetails footballClientDetails = new FootballClientDetails(clientDetails.getClientId(),
            passwordEncoder.encode(clientDetails.getClientSecret()),
            clientDetails.getScope(),
            clientDetails.getResourceIds(),
            clientDetails.getAuthorizedGrantTypes(),
            clientDetails.getRegisteredRedirectUri(),
            newArrayList(clientDetails.getAuthorities()),
            clientDetails.getAccessTokenValiditySeconds(),
            clientDetails.getRefreshTokenValiditySeconds(),
            clientDetails.getAdditionalInformation(),
            getAutoApproveScopes(clientDetails));

        footballClientDetailsRepository.save(footballClientDetails);
    }

    @Override
    public void updateClientDetails(final ClientDetails clientDetails) {
        final FootballClientDetails footballClientDetails = new FootballClientDetails(clientDetails.getClientId(),
            clientDetails.getClientSecret(),
            clientDetails.getScope(),
            clientDetails.getResourceIds(),
            clientDetails.getAuthorizedGrantTypes(),
            clientDetails.getRegisteredRedirectUri(),
            newArrayList(clientDetails.getAuthorities()),
            clientDetails.getAccessTokenValiditySeconds(),
            clientDetails.getRefreshTokenValiditySeconds(),
            clientDetails.getAdditionalInformation(),
            getAutoApproveScopes(clientDetails));
        try {
            footballClientDetailsRepository.save(footballClientDetails);
        } catch (Exception e) {
            throw new NoSuchClientException("No such Client Id");
        }
    }

    @Override
    public void updateClientSecret(final String clientId,
                                   final String secret) {
        try {
            FootballClientDetails footballClientDetails = footballClientDetailsRepository.findByClientId(clientId);
            footballClientDetails.setClientSecret(passwordEncoder.encode(secret));
            footballClientDetailsRepository.save(footballClientDetails);
        } catch (Exception e) {
            throw new NoSuchClientException("No such client id");
        }
    }

    @Override
    public void removeClientDetails(String clientId) {
        final boolean result = footballClientDetailsRepository.deleteByClientId(clientId);
        if (!result) {
            throw new NoSuchClientException("No such client id");
        }
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        final List<FootballClientDetails> allClientDetails = footballClientDetailsRepository.findAll();
        return newArrayList(allClientDetails);
    }

    private Set<String> getAutoApproveScopes(final ClientDetails clientDetails) {
        if (clientDetails.isAutoApprove("true")) {
            return newHashSet("true"); // all scopes autoapproved
        }

        return clientDetails.getScope().stream()
            .filter(clientDetails::isAutoApprove)
            .collect(Collectors.toSet());
    }
}
