package joaquinthiogo.inventorymanagementapi.model.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Unit;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {

    @NotBlank
    @Size(max = 200)
    private String noPart;

    @NotBlank
    private String description;

    private Integer hsCode;

    @NotBlank
    @Size(max = 100)
    private String itemType;

    @Valid
    private UnitRequest unit;

}
