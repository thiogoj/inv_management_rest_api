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
@Table(name = "currencies")
@EntityListeners(value = {
        AuditingEntityListener.class
})
public class Currency extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String currency;

    private String rate;

    private String remark;

}
