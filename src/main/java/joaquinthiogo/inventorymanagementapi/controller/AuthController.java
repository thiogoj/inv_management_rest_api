package joaquinthiogo.inventorymanagementapi.controller;

import joaquinthiogo.inventorymanagementapi.entity.masterdata.User;
import joaquinthiogo.inventorymanagementapi.model.WebResponse;
import joaquinthiogo.inventorymanagementapi.model.auth.LoginRequest;
import joaquinthiogo.inventorymanagementapi.model.auth.TokenResponse;
import joaquinthiogo.inventorymanagementapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);
        return WebResponse.<TokenResponse>builder()
                .data(response)
                .build();
    }

    @DeleteMapping(path = "/api/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> logout(User user) {
        authService.logout(user);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }

}
