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
@Table(name = "items")
@EntityListeners(value = {
        AuditingEntityListener.class
})
public class Item extends Auditable {

    @Id
    private String id;

    @Column(name = "no_part")
    private String noPart;

    private String description;

    @Column(name = "hs_code")
    private Integer hsCode;

    @Column(name = "item_type")
    private String itemType;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit;

}
