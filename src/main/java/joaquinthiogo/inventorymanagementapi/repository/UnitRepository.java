package joaquinthiogo.inventorymanagementapi.repository;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, String> {

    Optional<Unit> findFirstByName(String name);

}
