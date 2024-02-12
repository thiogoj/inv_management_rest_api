package joaquinthiogo.inventorymanagementapi.repository;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {


}
