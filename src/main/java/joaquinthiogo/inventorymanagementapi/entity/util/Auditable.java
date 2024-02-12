package joaquinthiogo.inventorymanagementapi.entity.util;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZoneId;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @CreatedDate
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_updated_at")
    private java.time.LocalDateTime lastUpdatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = java.time.LocalDateTime.now(ZoneId.of("Asia/Jakarta"));
        this.lastUpdatedAt = java.time.LocalDateTime.now(ZoneId.of("Asia/Jakarta"));
    }

    @PreUpdate
    public void onUpdate() {
        this.lastUpdatedAt = java.time.LocalDateTime.now(ZoneId.of("Asia/Jakarta"));
    }

}
