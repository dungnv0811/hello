package com.example.hello.repository.user;

import com.example.hello.domain.FootballClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FootballClientDetailsRepository extends MongoRepository<FootballClientDetails, String> {
    boolean deleteByClientId(String clientId);

//    boolean update(FootballClientDetails mongoClientDetails);

//    boolean updateClientSecret(String clientId, String newSecret);

    FootballClientDetails findByClientId(String clientId);
}
