package joaquinthiogo.inventorymanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Unit;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitRequest;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitResponse;
import joaquinthiogo.inventorymanagementapi.repository.ItemRepository;
import joaquinthiogo.inventorymanagementapi.repository.UnitRepository;
import joaquinthiogo.inventorymanagementapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class UnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
//        itemRepository.deleteAll();
//        unitRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("Test");
        user.setPassword("rahasia");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
        userRepository.save(user);
    }

    @Test
    void insertSuccess() throws Exception {
        UnitRequest request = new UnitRequest();
        request.setName("Pcs");

        mockMvc.perform(
                post("/api/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<UnitResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());
                    assertEquals("Pcs", response.getData().getName());

                    Unit unit = unitRepository.findById(response.getData().getId().toString()).orElse(null);
                    assertNotNull(unit);
                    assertEquals(unit.getId(), response.getData().getId());
                    assertEquals(unit.getName(), response.getData().getName());
                }
        );
    }

    @Test
    void insertUnauthorized() throws Exception {
        UnitRequest request = new UnitRequest();
        request.setName("Pcs");

        mockMvc.perform(
                post("/api/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isUnauthorized()
        ).andDo(
                result -> {
                    WebResponse<UnitResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void insertBadRequest() throws Exception {
        UnitRequest request = new UnitRequest();
        request.setName("");

        mockMvc.perform(
                post("/api/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<UnitResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void insertBadRequestUniqueName() throws Exception {
        Unit unit = new Unit();
        unit.setName("Desimeter");
        unitRepository.save(unit);

        UnitRequest request = new UnitRequest();
        request.setName("Desimeter");

        mockMvc.perform(
                post("/api/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<UnitResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void insertForeach() throws Exception {
        for (int i = 0; i < 20; i++) {
            UnitRequest request = new UnitRequest();
            request.setName("Pcs " + i);

            mockMvc.perform(
                    post("/api/unit")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .header("X-API-TOKEN", "test")
                            .content(objectMapper.writeValueAsString(request))
            ).andExpect(
                    status().isOk()
            );
        }
    }

    @Test
    void updateSuccess() throws Exception {
        Unit unit = new Unit();
        unit.setName("Kg");
        unitRepository.save(unit);

        UnitRequest request = new UnitRequest();
        request.setName("Meter");

        mockMvc.perform(
                put("/api/unit/" + unit.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<UnitResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());
                    assertEquals("Meter", response.getData().getName());

                    Unit unitDB = unitRepository.findById(response.getData().getId().toString()).orElse(null);
                    assertNotNull(unitDB);
                    assertEquals(unitDB.getId(), response.getData().getId());
                    assertEquals(unitDB.getName(), response.getData().getName());
                }
        );
    }

    @Test
    void updateBadRequest() throws Exception {
        Unit unit = new Unit();
        unit.setName("Kg");
        unitRepository.save(unit);

        UnitRequest request = new UnitRequest();
        request.setName("");

        mockMvc.perform(
                put("/api/unit/" + unit.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<UnitResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void updateAlreadyExist() throws Exception {
        Unit unit = new Unit();
        unit.setName("Kg");
        unitRepository.save(unit);

        UnitRequest request = new UnitRequest();
        request.setName("Kg");

        mockMvc.perform(
                put("/api/unit/" + unit.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<UnitResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void deleteSuccess() throws Exception {
        Unit unit = new Unit();
        unit.setName("Kg");
        unitRepository.save(unit);

        mockMvc.perform(
                delete("/api/unit/" + unit.getId())
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
                    assertEquals("OK", response.getData());
                }
        );
    }

    @Test
    void deleteIfItemIsEmpty() throws Exception {
        Unit unit = unitRepository.findFirstByName("Kg").orElse(null);

        mockMvc.perform(
                delete("/api/unit/" + unit.getId())
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
                    assertEquals("OK", response.getData());
                }
        );
    }

    @Test
    void deleteNotFound() throws Exception {
        Unit unit = new Unit();
        unit.setName("Kg");
        unitRepository.save(unit);

        mockMvc.perform(
                delete("/api/unit/123")
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

    @Test
    void findById() throws Exception {
        Unit unit = new Unit();
        unit.setName("Test");
        unitRepository.save(unit);

        mockMvc.perform(
                get("/api/unit/" + unit.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<UnitResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());
                    assertEquals("Test", response.getData().getName());

                    assertNotNull(unit);
                    assertEquals(unit.getId(), response.getData().getId());
                    assertEquals(unit.getName(), response.getData().getName());
                }
        );
    }
}