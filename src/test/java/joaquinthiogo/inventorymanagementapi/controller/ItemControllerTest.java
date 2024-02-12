package joaquinthiogo.inventorymanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Item;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Unit;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.item.ItemRequest;
import joaquinthiogo.inventorymanagementapi.model.item.ItemResponse;
import joaquinthiogo.inventorymanagementapi.model.unit.UnitRequest;
import joaquinthiogo.inventorymanagementapi.repository.ItemRepository;
import joaquinthiogo.inventorymanagementapi.repository.UnitRepository;
import joaquinthiogo.inventorymanagementapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @BeforeEach
    void setUp() {
//        itemRepository.deleteAll();
        userRepository.deleteAll();
//        unitRepository.deleteAll();

        User user = new User();
        user.setUsername("Test");
        user.setPassword("rahasia");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
        userRepository.save(user);
    }

    @Test
    void insertSuccess() throws Exception {
//        Unit unit = new Unit();
//        unit.setName("Kg");
//        unitRepository.save(unit);

        UnitRequest unitRequest = new UnitRequest();
        unitRequest.setName("Kg");

        ItemRequest request = new ItemRequest();
        request.setNoPart("9RP5");
        request.setHsCode(1234567890);
        request.setDescription("Alumni Skansa");
        request.setItemType("Barang Jadi");
        request.setUnit(unitRequest);

        mockMvc.perform(
                post("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ItemResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    Item item = itemRepository.findById(response.getData().getId()).orElse(null);
                    assertNotNull(item);
                    assertEquals(item.getId(), response.getData().getId());
                    assertEquals(item.getNoPart(), response.getData().getNoPart());
                    assertEquals(item.getDescription(), response.getData().getDescription());
                    assertEquals(item.getHsCode(), response.getData().getHsCode());
                    assertEquals(item.getItemType(), response.getData().getItemType());
                    assertEquals(item.getUnit().getName(), response.getData().getUnit().getName());
                }
        );
    }

    @Test
    void insertBadRequest() throws Exception {
        Unit unit = new Unit();
        unit.setName("Meter");
        unitRepository.save(unit);

        UnitRequest unitRequest = new UnitRequest();
        unitRequest.setName("");

        ItemRequest request = new ItemRequest();
        request.setNoPart("");
        request.setHsCode(9321021);
        request.setDescription("Ini description");
        request.setItemType("");
        request.setUnit(unitRequest);

        mockMvc.perform(
                post("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<ItemResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void insertForeach() throws Exception {
        Unit unit = new Unit();
        unit.setName("Meter");
        unitRepository.save(unit);

        for (int i = 0; i < 30; i++) {
            UnitRequest unitRequest = new UnitRequest();
            unitRequest.setName("Meter");

            ItemRequest request = new ItemRequest();
            request.setNoPart("9RP5");
            request.setHsCode(9321021);
            request.setDescription("Ini description");
            request.setItemType("Barang Jadi");
            request.setUnit(unitRequest);

            mockMvc.perform(
                    post("/api/item")
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

        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setNoPart("9RP5");
        item.setHsCode(9321021);
        item.setDescription("Ini description");
        item.setItemType("Barang Jadi");
        item.setUnit(unit);
        itemRepository.save(item);

        UnitRequest unitRequest = new UnitRequest();
        unitRequest.setName("Kg");

        ItemRequest request = new ItemRequest();
        request.setNoPart("9UPDATE");
        request.setHsCode(23132);
        request.setDescription("Ini update");
        request.setItemType("Barang Test");
        request.setUnit(unitRequest);


        mockMvc.perform(
                put("/api/item/" + item.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ItemResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    Item itemDB = itemRepository.findById(response.getData().getId()).orElse(null);
                    assertNotNull(itemDB);
                    assertEquals(itemDB.getId(), response.getData().getId());
                    assertEquals(itemDB.getNoPart(), response.getData().getNoPart());
                    assertEquals(itemDB.getDescription(), response.getData().getDescription());
                    assertEquals(itemDB.getHsCode(), response.getData().getHsCode());
                    assertEquals(itemDB.getItemType(), response.getData().getItemType());
                    assertEquals(itemDB.getUnit().getName(), response.getData().getUnit().getName());
                }
        );
    }

    @Test
    void updateFailed() throws Exception {
        Unit unit = new Unit();
        unit.setName("Kg");
        unitRepository.save(unit);

        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setNoPart("9RP5");
        item.setHsCode(9321021);
        item.setDescription("Ini description");
        item.setItemType("Barang Jadi");
        item.setUnit(unit);
        itemRepository.save(item);

        UnitRequest unitRequest = new UnitRequest();
        unitRequest.setName("Kg");

        ItemRequest request = new ItemRequest();
        request.setNoPart("9UPDATE");
        request.setHsCode(23132);
        request.setDescription("");
        request.setItemType("Barang Test");
        request.setUnit(unitRequest);

        mockMvc.perform(
                put("/api/item/" + "sdadwa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isNotFound()
        ).andDo(
                result -> {
                    WebResponse<ItemResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
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

        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setNoPart("9RP5");
        item.setHsCode(9321021);
        item.setDescription("Ini description");
        item.setItemType("Barang Jadi");
        item.setUnit(unit);
        itemRepository.save(item);

        mockMvc.perform(
                delete("/api/item/" + item.getId())
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
    void deleteNotfound() throws Exception {
        Unit unit = new Unit();
        unit.setName("Kg");
        unitRepository.save(unit);

        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setNoPart("9RP5");
        item.setHsCode(9321021);
        item.setDescription("Ini description");
        item.setItemType("Barang Jadi");
        item.setUnit(unit);
        itemRepository.save(item);

        mockMvc.perform(
                delete("/api/item/" + 123)
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
        unit.setName("Kg");
        unitRepository.save(unit);

        Item item = new Item();
        item.setId(UUID.randomUUID().toString());
        item.setNoPart("9RP5");
        item.setHsCode(9321021);
        item.setDescription("Ini description");
        item.setItemType("Barang Jadi");
        item.setUnit(unit);
        itemRepository.save(item);

        mockMvc.perform(
                get("/api/item/" + item.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
            status().isOk()
        ).andDo(
                result -> {
                    WebResponse<ItemResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    assertEquals(item.getId(), response.getData().getId());
                    assertEquals(item.getNoPart(), response.getData().getNoPart());
                    assertEquals(item.getDescription(), response.getData().getDescription());
                    assertEquals(item.getHsCode(), response.getData().getHsCode());
                    assertEquals(item.getItemType(), response.getData().getItemType());
                    assertEquals(item.getUnit().getName(), response.getData().getUnit().getName());
                }
        );
    }
}