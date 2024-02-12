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
@Table(name = "suppliers")
@EntityListeners(value = {
        AuditingEntityListener.class
})
public class Supplier extends Auditable {

    @Id
    private String id;

    private String name;

    private String address;

    private String phone;

}
