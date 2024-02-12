package joaquinthiogo.inventorymanagementapi.model.supplier;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierResponse {

    private String id;

    private String name;

    private String address;

    private String phone;

}
