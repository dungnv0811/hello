package com.example.hello.security;

import com.example.hello.domain.FootballApproval;
import com.example.hello.repository.user.FootballApprovalRepository;
import com.example.hello.util.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.hello.util.LocalDateTimeUtil.convertTolocalDateTimeFrom;
import static java.util.Objects.isNull;

@Component
public class FootballApprovalStore implements ApprovalStore {

    private final FootballApprovalRepository footballApprovalRepository;

    private boolean handleRevocationsAsExpiry = false;
    private DateHelper dateHelper;

    @Autowired
    public FootballApprovalStore(FootballApprovalRepository footballApprovalRepository) {
        this.footballApprovalRepository = footballApprovalRepository;
    }

    @Override
    public boolean addApprovals(final Collection<Approval> approvals) {

        final Collection<FootballApproval> footballApprovals = transformToFootballApproval(approvals);

        try {
            footballApprovalRepository.saveAll(footballApprovals);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean revokeApprovals(final Collection<Approval> approvals) {
        boolean success = true;

        final Collection<FootballApproval> footballApprovals = transformToFootballApproval(approvals);

        for (final FootballApproval footballApproval : footballApprovals) {
            if (handleRevocationsAsExpiry) {
                FootballApproval existedFootballApproval = footballApprovalRepository.findById(footballApproval.getId()).get();
                existedFootballApproval.setExpiresAt(LocalDateTime.now());

                try {
                    footballApprovalRepository.save(existedFootballApproval);
                } catch (Exception e) {
                    success = false;
                }
            }
            else {
                final boolean deleteResult = footballApprovalRepository.deleteByUserIdAndClientIdAndScope(footballApproval);

                if (!deleteResult) {
                    success = false;
                }
            }
        }
        return success;
    }

    @Override
    public Collection<Approval> getApprovals(final String userId,
                                             final String clientId) {
        final List<FootballApproval> footballApprovals = footballApprovalRepository.findByUserIdAndClientId(userId, clientId);
        return transformToApprovals(footballApprovals);
    }

    private List<Approval> transformToApprovals(final List<FootballApproval> footballApprovals) {
        return footballApprovals.stream().map(footballApproval -> new Approval(footballApproval.getUserId(),
            footballApproval.getClientId(),
            footballApproval.getScope(),
            Date.from(footballApproval.getExpiresAt().atZone(ZoneId.systemDefault()).toInstant()),
            footballApproval.getStatus(),
            Date.from(footballApproval.getLastUpdatedAt().atZone(ZoneId.systemDefault()).toInstant())))
            .collect(Collectors.toList());
    }

    private List<FootballApproval> transformToFootballApproval(final Collection<Approval> approvals) {
        return approvals.stream().map(approval -> new FootballApproval(UUID.randomUUID().toString(),
            approval.getUserId(),
            approval.getClientId(),
            approval.getScope(),
            isNull(approval.getStatus()) ? Approval.ApprovalStatus.APPROVED: approval.getStatus(),
            convertTolocalDateTimeFrom(approval.getExpiresAt()),
            convertTolocalDateTimeFrom(approval.getLastUpdatedAt()))).collect(Collectors.toList());
    }

    public void setHandleRevocationsAsExpiry(boolean handleRevocationsAsExpiry) {
        this.handleRevocationsAsExpiry = handleRevocationsAsExpiry;
    }
}