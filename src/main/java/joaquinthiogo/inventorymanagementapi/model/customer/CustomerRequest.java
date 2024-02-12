package joaquinthiogo.inventorymanagementapi.model.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    @NotBlank
    private String name;

    private String address;

    @Size(max = 15)
    @Pattern(regexp = "\\d*", message = "Phone number must contain only digits")
    private String phone;

}
