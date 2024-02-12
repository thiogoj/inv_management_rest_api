package joaquinthiogo.inventorymanagementapi.model.currency;

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
public class CurrencyRequest {

    @NotBlank
    @Size(max = 100)
    private String currency;

    @NotBlank
    @Pattern(regexp = "^[0-9.]*$", message = "Rate should contain only digits and/or dot")
    private String rate;

    @NotBlank
    private String remark;


}
