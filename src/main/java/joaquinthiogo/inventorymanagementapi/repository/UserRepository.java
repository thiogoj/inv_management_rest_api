package joaquinthiogo.inventorymanagementapi.repository;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByUsernameAndPassword(String username, String password);

    Optional<User> findFirstByToken(String token);

}
