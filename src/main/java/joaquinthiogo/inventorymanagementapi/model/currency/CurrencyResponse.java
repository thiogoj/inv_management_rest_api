package joaquinthiogo.inventorymanagementapi.model.currency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyResponse {

    private Integer id;

    private String currency;

    private String rate;

    private String remark;

    private LocalDateTime lastUpdatedAt;

}
