package joaquinthiogo.inventorymanagementapi.entity.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import joaquinthiogo.inventorymanagementapi.entity.util.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "units")
@EntityListeners(value = {
        AuditingEntityListener.class
})
public class Unit extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "unit")
    private List<Item> items;

}
