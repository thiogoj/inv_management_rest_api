package joaquinthiogo.inventorymanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Customer;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.customer.CustomerRequest;
import joaquinthiogo.inventorymanagementapi.model.customer.CustomerResponse;
import joaquinthiogo.inventorymanagementapi.repository.CustomerRepository;
import joaquinthiogo.inventorymanagementapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        customerRepository.deleteAll();;

        User user = new User();
        user.setUsername("Test");
        user.setPassword("rahasia");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
        userRepository.save(user);
    }

    @Test
    void insertSuccess() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setName("Udin");

        mockMvc.perform(
                post("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<CustomerRequest> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());
                    assertEquals(request.getName(), response.getData().getName());
                    assertEquals(request.getAddress(), response.getData().getAddress());
                    assertEquals(request.getPhone(), response.getData().getPhone());
                }
        );
    }

    @Test
    void insertBadRequest() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setName("");
        request.setAddress("Nilam 1");

        mockMvc.perform(
                post("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<CustomerRequest> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void findAll() throws Exception {
        for (int i = 0; i < 15; i++) {
            Customer customer = new Customer();
            customer.setId(UUID.randomUUID().toString());
            customer.setName("Customer " + i);
            customer.setAddress("Address " + i);
            customer.setPhone("Phone " + i);
            customerRepository.save(customer);
        }

        mockMvc.perform(
                get("/api/customer?pageNumber=0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<List<CustomerResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    assertEquals(response.getPaging().getCurrentPage(), 0);
                    assertEquals(response.getPaging().getTotalPage(), 2);
                    assertEquals(response.getPaging().getSize(), 10);
                }
        );
    }

    @Test
    void findByIdSuccess() throws Exception {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName("Test");
        customer.setAddress("Test");
        customer.setPhone("08123");
        customerRepository.save(customer);

        mockMvc.perform(
                get("/api/customer/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    Customer customerDB = customerRepository.findById(customer.getId()).orElse(null);
                    assertEquals(customerDB.getId(), response.getData().getId());
                    assertEquals(customerDB.getName(), response.getData().getName());
                    assertEquals(customerDB.getAddress(), response.getData().getAddress());
                    assertEquals(customerDB.getPhone(), response.getData().getPhone());
                }
        );
    }

    @Test
    void findByIdNotFound() throws Exception {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName("Test");
        customer.setAddress("Test");
        customer.setPhone("08123");
        customerRepository.save(customer);

        mockMvc.perform(
                get("/api/customer/" + 123)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void updateSuccess() throws Exception {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName("Test");
        customer.setAddress("Test");
        customer.setPhone("08123");
        customerRepository.save(customer);

        CustomerRequest request = new CustomerRequest();
        request.setName("Updated");
        request.setAddress("Updated address");
        request.setPhone("081231");

        mockMvc.perform(
                put("/api/customer/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    Customer customerDB = customerRepository.findById(customer.getId()).orElse(null);
                    assertEquals(customerDB.getId(), response.getData().getId());
                    assertEquals(customerDB.getName(), response.getData().getName());
                    assertEquals(customerDB.getAddress(), response.getData().getAddress());
                    assertEquals(customerDB.getPhone(), response.getData().getPhone());
                }
        );
    }

    @Test
    void updateBadRequest() throws Exception {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName("Test");
        customer.setAddress("Test");
        customer.setPhone("08123");
        customerRepository.save(customer);

        CustomerRequest request = new CustomerRequest();
        request.setName("");
        request.setAddress("Updated address");
        request.setPhone("wdawd");

        mockMvc.perform(
                put("/api/customer/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void deleteSuccess() throws Exception{
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName("Test");
        customer.setAddress("Test");
        customer.setPhone("08123");
        customerRepository.save(customer);

        mockMvc.perform(
                delete("/api/customer/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());
                }
        );
    }

    @Test
    void deletetNotFound() throws Exception{
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName("Test");
        customer.setAddress("Test");
        customer.setPhone("08123");
        customerRepository.save(customer);

        mockMvc.perform(
                delete("/api/customer/" + 123)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }
}