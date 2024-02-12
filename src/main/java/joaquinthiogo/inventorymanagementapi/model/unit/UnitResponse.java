package joaquinthiogo.inventorymanagementapi.model.unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitResponse {

    private Integer id;

    private String name;

}
