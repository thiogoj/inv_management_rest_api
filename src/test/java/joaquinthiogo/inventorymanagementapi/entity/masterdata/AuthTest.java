package joaquinthiogo.inventorymanagementapi.entity.masterdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.auth.LoginRequest;
import joaquinthiogo.inventorymanagementapi.model.auth.TokenResponse;
import joaquinthiogo.inventorymanagementapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void insertUser() {
        User user = new User();
        user.setUsername("Haloo");
        user.setPassword("rahasia123");
        userRepository.save(user);

        User userDb = userRepository.findById(user.getId().toString()).orElse(null);
        assertNotNull(userDb);
        assertNotNull(userDb.getCreatedAt());
    }

    @Test
    void loginBlank() throws Exception {
        User user = new User();
        user.setUsername("Haloo");
        user.setPassword("rahasia123");
        userRepository.save(user);

        LoginRequest request = new LoginRequest();
        request.setUsername("");
        request.setPassword("");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void loginWrongPassword() throws Exception {
        User user = new User();
        user.setUsername("Haloo");
        user.setPassword("rahasia123");
        userRepository.save(user);

        LoginRequest request = new LoginRequest();
        request.setUsername("test");
        request.setPassword("etada");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(
                result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertNotNull(response.getErrors());
                }
        );
    }

    @Test
    void loginSuccess() throws Exception {
        User user = new User();
        user.setUsername("Haloo");
        user.setPassword("rahasia123");
        userRepository.save(user);

        LoginRequest request = new LoginRequest();
        request.setUsername("Haloo");
        request.setPassword("rahasia123");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    WebResponse<TokenResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getData().getToken());
                    assertNotNull(response.getData().getExpiredAt());

                    User userDB = userRepository.findFirstByUsernameAndPassword(request.getUsername(), request.getPassword()).orElse(null);
                    assertEquals(userDB.getToken(), response.getData().getToken());
                    assertEquals(userDB.getTokenExpiredAt(), response.getData().getExpiredAt());
                }
        );
    }

    @Test
    void logoutFailed() throws Exception {
        mockMvc.perform(
                delete("/api/auth/logout")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo( result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    Assertions.assertNotNull(response.getErrors());
                }
        );
    }
}