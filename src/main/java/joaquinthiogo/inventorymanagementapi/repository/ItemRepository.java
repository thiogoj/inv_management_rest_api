package joaquinthiogo.inventorymanagementapi.repository;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.Item;
import joaquinthiogo.inventorymanagementapi.model.item.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    @Query(value = "select i from Item i where i.description like :description")
    List<Item> searchItemsUsingDescription(@Param("description") String description);

    List<Item> findAllByUnitId(Integer unitId);

}
