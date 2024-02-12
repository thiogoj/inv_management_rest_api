package joaquinthiogo.inventorymanagementapi.repository;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {
}
