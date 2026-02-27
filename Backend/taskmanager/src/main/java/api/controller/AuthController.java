package api.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.ApiResponse;
import api.dto.LoginRequest;
import api.dto.LoginResponse;
import api.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login( @RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.login(request);        
        return ResponseEntity.ok(
            new ApiResponse("Success", HttpStatus.OK.value(), "Logueado correctamente", loginResponse)
        );
    }
}
