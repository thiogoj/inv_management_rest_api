package joaquinthiogo.inventorymanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Supplier;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.supplier.SupplierRequest;
import joaquinthiogo.inventorymanagementapi.model.supplier.SupplierResponse;
import joaquinthiogo.inventorymanagementapi.repository.SupplierRepository;
import joaquinthiogo.inventorymanagementapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        supplierRepository.deleteAll();

        User user = new User();
        user.setUsername("Test");
        user.setPassword("rahasia");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
        userRepository.save(user);
    }

    @Test
    void insertSuccess() throws Exception {
        SupplierRequest request = new SupplierRequest();
        request.setName("United Airlines 3");
        request.setAddress("Singapore 3");
        request.setPhone("082178274067");

        mockMvc.perform(
                post("/api/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<SupplierResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    Supplier supplier = supplierRepository.findById(response.getData().getId()).orElse(null);
                    assertNotNull(supplier);
                    assertEquals(supplier.getName(), response.getData().getName());
                    assertEquals(supplier.getAddress(), response.getData().getAddress());
                    assertEquals(supplier.getPhone(), response.getData().getPhone());
                }
        );
    }

    @Test
    void insertBadRequest() throws Exception {
        SupplierRequest request = new SupplierRequest();
        request.setName("United Airlines");
        request.setAddress("");
        request.setPhone("123");

        mockMvc.perform(
                post("/api/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<SupplierResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void insertBadRequestJustNumber() throws Exception {
        SupplierRequest request = new SupplierRequest();
        request.setName("United Airlines");
        request.setAddress("");
        request.setPhone("123adawda");

        mockMvc.perform(
                post("/api/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<SupplierResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void insertForeach() throws Exception{
        for (int i = 0; i < 20; i++) {
            SupplierRequest request = new SupplierRequest();
            request.setName("United Airlines " + i);
            request.setAddress("Singapore " + i);

            MvcResult result = mockMvc.perform(
                    post("/api/supplier")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .header("X-API-TOKEN", "test")
                            .content(objectMapper.writeValueAsString(request))
            ).andExpect(
                    status().isOk()
            ).andReturn(); // Menggunakan andReturn() untuk mendapatkan MvcResult

            WebResponse<SupplierResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());

            Supplier supplier = supplierRepository.findById(response.getData().getId()).orElse(null);
            assertNotNull(supplier);
            assertEquals(supplier.getName(), response.getData().getName());
            assertEquals(supplier.getAddress(), response.getData().getAddress());
            assertEquals(supplier.getPhone(), response.getData().getPhone());
        }
    }

    @Test
    void findByIdSuccess() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setId(UUID.randomUUID().toString());
        supplier.setName("United Airlines");
        supplier.setAddress("Singapore");
        supplier.setPhone("08217827");
        supplierRepository.save(supplier);

        mockMvc.perform(
                get("/api/supplier/" + supplier.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<SupplierResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    Supplier supplierDB = supplierRepository.findById(response.getData().getId()).orElse(null);
                    assertNotNull(supplierDB);
                    assertEquals(supplierDB.getName(), response.getData().getName());
                    assertEquals(supplierDB.getAddress(), response.getData().getAddress());
                    assertEquals(supplierDB.getPhone(), response.getData().getPhone());
                }
        );
    }

    @Test
    void findByIdBadRequest() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setId(UUID.randomUUID().toString());
        supplier.setName("United Airlines");
        supplier.setAddress("Singapore");
        supplier.setPhone("1234");
        supplierRepository.save(supplier);

        mockMvc.perform(
                get("/api/supplier/" + 123)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<SupplierResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void updateSuccess() throws Exception{
        Supplier supplier = new Supplier();
        supplier.setId(UUID.randomUUID().toString());
        supplier.setName("United Airlines");
        supplier.setAddress("Singapore");
        supplier.setPhone("1234");
        supplierRepository.save(supplier);

        SupplierRequest request = new SupplierRequest();
        request.setName("Updated");
        request.setAddress("Updated Address");
        request.setPhone("1234");

        mockMvc.perform(
                put("/api/supplier/" + supplier.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<SupplierResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    Supplier supplierDB = supplierRepository.findById(response.getData().getId()).orElse(null);
                    assertNotNull(supplierDB);
                    assertEquals(supplierDB.getName(), response.getData().getName());
                    assertEquals(supplierDB.getAddress(), response.getData().getAddress());
                    assertEquals(supplierDB.getPhone(), response.getData().getPhone());
                }
        );
    }

    @Test
    void deleteSuccess() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setId(UUID.randomUUID().toString());
        supplier.setName("United Airlines");
        supplier.setAddress("Singapore");
        supplier.setPhone("1234");
        supplierRepository.save(supplier);

        mockMvc.perform(
                delete("/api/supplier/" + supplier.getId())
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

                    Supplier supplierDB = supplierRepository.findById(supplier.getId()).orElse(null);
                    assertNull(supplierDB);
                }
        );
    }

    @Test
    void deleteNotFound() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setId(UUID.randomUUID().toString());
        supplier.setName("United Airlines");
        supplier.setAddress("Singapore");
        supplier.setPhone("1234");
        supplierRepository.save(supplier);

        mockMvc.perform(
                delete("/api/supplier/" + 123)
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