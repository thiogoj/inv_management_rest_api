package joaquinthiogo.inventorymanagementapi.model.item;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Unit;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {

    private String id;

    private String noPart;

    private String description;

    private Integer hsCode;

    private String itemType;

    private UnitResponse unit;

}
