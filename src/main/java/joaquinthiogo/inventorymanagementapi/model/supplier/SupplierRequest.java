package joaquinthiogo.inventorymanagementapi.model.supplier;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierRequest {

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotBlank
    private String address;

    @Size(max = 15)
    @Pattern(regexp = "\\d*", message = "Phone number must contain only digits")
    private String phone;

}
