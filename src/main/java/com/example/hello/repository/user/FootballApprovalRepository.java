package com.example.hello.repository.user;

import com.example.hello.domain.FootballApproval;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface FootballApprovalRepository extends MongoRepository<FootballApproval, String> {
//    boolean updateOrCreate(Collection<FootballApproval> footballApprovals);

//    boolean updateExpiresAt(LocalDateTime now, FootballApproval footballApproval);

    boolean deleteByUserIdAndClientIdAndScope(FootballApproval footballApproval);

    List<FootballApproval> findByUserIdAndClientId(String userId, String clientId);
}
