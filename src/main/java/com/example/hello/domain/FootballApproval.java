package com.example.hello.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;

import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "radio_approval")
@Data
public class FootballApproval {

    @Id
    private String id;
    private String userId;
    private String clientId;
    private String scope;
    private ApprovalStatus status;
    private LocalDateTime expiresAt;
    private LocalDateTime lastUpdatedAt;

    public FootballApproval(final String id,
                            final String userId,
                            final String clientId,
                            final String scope,
                            final ApprovalStatus status,
                            final LocalDateTime expiresAt,
                            final LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.scope = scope;
        this.status = status;
        this.expiresAt = expiresAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getScope() {
        return scope;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, clientId, scope, status, expiresAt, lastUpdatedAt);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FootballApproval other = (FootballApproval) obj;
        return Objects.equals(this.userId, other.userId)
            && Objects.equals(this.clientId, other.clientId)
            && Objects.equals(this.scope, other.scope)
            && Objects.equals(this.status, other.status)
            && Objects.equals(this.expiresAt, other.expiresAt)
            && Objects.equals(this.lastUpdatedAt, other.lastUpdatedAt);
    }

    @Override
    public String toString() {
        return "FootballApproval{" +
            "id='" + id + '\'' +
            ", userId='" + userId + '\'' +
            ", clientId='" + clientId + '\'' +
            ", scope='" + scope + '\'' +
            ", status=" + status +
            ", expiresAt=" + expiresAt +
            ", lastUpdatedAt=" + lastUpdatedAt +
            '}';
    }
}
