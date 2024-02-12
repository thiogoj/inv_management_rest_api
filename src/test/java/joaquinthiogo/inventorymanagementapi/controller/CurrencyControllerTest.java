package joaquinthiogo.inventorymanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.Currency;
import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.currency.CurrencyRequest;
import joaquinthiogo.inventorymanagementapi.model.currency.CurrencyResponse;
import joaquinthiogo.inventorymanagementapi.repository.CurrencyRepository;
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
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        currencyRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("Test");
        user.setPassword("rahasia");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000000L);
        userRepository.save(user);
    }

    @Test
    void insertSuccess() throws Exception {
        CurrencyRequest request = new CurrencyRequest();
        request.setCurrency("USD");
        request.setRate("25.00");
        request.setRemark("United States Dollar");

        mockMvc.perform(
                post("/api/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<CurrencyResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNull(response.getErrors());

                    Currency currency = currencyRepository.findById(response.getData().getId().toString()).orElse(null);
                    assertEquals(currency.getId(), response.getData().getId());
                    assertEquals(currency.getCurrency(), response.getData().getCurrency());
                    assertEquals(currency.getRate(), response.getData().getRate());
                    assertEquals(currency.getRemark(), response.getData().getRemark());
                }
        );
    }

    @Test
    void insertBadRequest() throws Exception {
        CurrencyRequest request = new CurrencyRequest();
        request.setCurrency("");
        request.setRate("");
        request.setRemark("United States Dollar");

        mockMvc.perform(
                post("/api/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<CurrencyResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void updateSuccess() throws Exception {
        Currency currency = new Currency();
        currency.setCurrency("INI TEST");
        currency.setRate("10.00");
        currency.setRemark("Ini Test Dollar");
        currencyRepository.save(currency);

        CurrencyRequest request = new CurrencyRequest();
        request.setCurrency("TEST");
        request.setRate("15.00");
        request.setRemark("Test Dollar");

        mockMvc.perform(
                put("/api/currency/" + currency.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<CurrencyResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    assertEquals(request.getCurrency(), response.getData().getCurrency());
                    assertEquals(request.getRate(), response.getData().getRate());
                    assertEquals(request.getRemark(), response.getData().getRemark());
                }
        );
    }

    @Test
    void updateBadRequest() throws Exception {
        CurrencyRequest request = new CurrencyRequest();
        request.setCurrency("");
        request.setRate("15.00");
        request.setRemark("Test Dollar");

        mockMvc.perform(
                put("/api/currency/1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<CurrencyResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void destroySuccess() throws Exception {
        Currency currency = new Currency();
        currency.setCurrency("INI TEST");
        currency.setRate("10.00");
        currency.setRemark("Ini Test Dollar");
        currencyRepository.save(currency);

        mockMvc.perform(
                delete("/api/currency/" + currency.getId())
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
    void destroyNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/currency/123")
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
        Currency currency = new Currency();
        currency.setCurrency("INI TEST");
        currency.setRate("10.00");
        currency.setRemark("Ini Test Dollar");
        currencyRepository.save(currency);

        mockMvc.perform(
                get("/api/currency/" + currency.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<CurrencyResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getErrors());

                    assertEquals(currency.getId(), response.getData().getId());
                    assertEquals(currency.getCurrency(), response.getData().getCurrency());
                    assertEquals(currency.getRate(), response.getData().getRate());
                    assertEquals(currency.getRemark(), response.getData().getRemark());
                }
        );
    }
}