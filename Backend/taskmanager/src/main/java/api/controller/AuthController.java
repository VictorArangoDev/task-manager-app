package api.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.ApiResponse;
import api.dto.LoginRequest;
import api.dto.LoginResponse;
import api.dto.UserResponse;
import api.model.User;
import api.repository.UserRepository;
import api.service.UserService;
import api.utils.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(UserService userService, JwtUtil jwtUtil, UserRepository userRepository){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login( @RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.login(request);        
        return ResponseEntity.ok(
            new ApiResponse("Success", HttpStatus.OK.value(), "Logueado correctamente", loginResponse)
        );
    }

       @GetMapping("/check-status")
    public ResponseEntity<ApiResponse> checkStatus(
        @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse("Error", HttpStatus.UNAUTHORIZED.value(), "Token no proporcionado", null)
            );
        }

        String token = authorizationHeader.substring(7);

        try {
            String username = jwtUtil.extractUsername(token);
            
            // Obtener el usuario completo de la base de datos
            User user = userRepository.findByUsername(username).orElse(null);
            
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse("Error", HttpStatus.UNAUTHORIZED.value(), "Usuario no encontrado", null)
                );
            }

            String roleName = user.getRole() != null ? user.getRole().getName() : null;

            UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getDocument(),
                user.getEmail(),
                roleName,
                user.getImage(),
                user.getState(),
                user.getCreatedAt(),
                user.getUpdatedAt()
            );
            
            // Crear la misma estructura que devuelve el login: { user, token }
            LoginResponse response = new LoginResponse(userResponse, token);

            return ResponseEntity.ok(
                new ApiResponse("Success", HttpStatus.OK.value(), "Token válido", response)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse("Error", HttpStatus.UNAUTHORIZED.value(), "Token inválido o expirado", null)
            );
        }
    }
}

    // @GetMapping("/check-status")
    // public ResponseEntity<ApiResponse> checkStatus(
    //     @RequestHeader(value = "Authorization", required = false) String authorizationHeader
    // ) {

    //     if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
    //             new ApiResponse("Error", HttpStatus.UNAUTHORIZED.value(), "Token no proporcionado", null)
    //         );
    //     }

    //     String token = authorizationHeader.substring(7);

    //     try {
    //         String username = jwtUtil.extractUsername(token);
    //         String role = jwtUtil.extractRole(token);

    //         Map<String, Object> data = new HashMap<>();
    //         data.put("username", username);
    //         data.put("role", role);

    //         return ResponseEntity.ok(
    //             new ApiResponse("Success", HttpStatus.OK.value(), "Token válido", data)
    //         );

    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
    //             new ApiResponse("Error", HttpStatus.UNAUTHORIZED.value(), "Token inválido o expirado", null)
    //         );
    //     }
    // }
//}
