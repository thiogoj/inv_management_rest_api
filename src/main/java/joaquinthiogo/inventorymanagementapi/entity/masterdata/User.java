package joaquinthiogo.inventorymanagementapi.entity.masterdata;

import jakarta.persistence.*;
import joaquinthiogo.inventorymanagementapi.entity.util.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners({
        AuditingEntityListener.class
})
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;


}
