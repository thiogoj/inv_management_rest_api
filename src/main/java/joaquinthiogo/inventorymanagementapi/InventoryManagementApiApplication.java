package joaquinthiogo.inventorymanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
public class InventoryManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApiApplication.class, args);
	}

}
