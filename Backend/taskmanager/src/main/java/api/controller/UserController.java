package api.controller;



import org.springframework.web.bind.annotation.*;

import api.dto.ApiResponse;
import api.model.User;
import api.service.UserService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {
        private final UserService userService;

        public UserController(UserService userService) {
                this.userService = userService;
        }

        @PostMapping
        public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
                User savedUser = userService.crearUsuario(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(
                                new ApiResponse("success", HttpStatus.CREATED.value(), "Usuario Creado correctamente",
                                                savedUser));
        }

        @GetMapping
        public ResponseEntity<ApiResponse> findAll() {
                List<User> users = userService.finAll();
                if (users.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                        .body(new ApiResponse("success", HttpStatus.NO_CONTENT.value(),
                                                        "No hay usuarios registrados", users));
                }
                return ResponseEntity.ok(
                                new ApiResponse("success", HttpStatus.OK.value(),
                                                "Lista de usuarios obtenida correctamente", users));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse> findByID(@PathVariable Long id) {
                return userService.findById(id)
                                .map(user -> ResponseEntity.ok(
                                                new ApiResponse("success", HttpStatus.OK.value(), "Usuario encontrado",
                                                                user)))
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(new ApiResponse("error", HttpStatus.NOT_FOUND.value(),
                                                                "Usuario no encontrado", null)));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody User userDetails) {
                return userService.update(id, userDetails)
                                .map(updatedUser -> ResponseEntity.ok(
                                                new ApiResponse("success", HttpStatus.OK.value(),
                                                                "Usuario actualizado correctamente", updatedUser)))
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(new ApiResponse("error", HttpStatus.NOT_FOUND.value(),
                                                                "Usuario no encontrado", null)));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
                boolean deleted = userService.delete(id);
                if (deleted) {
                        return ResponseEntity.ok(
                                        new ApiResponse("success", HttpStatus.OK.value(),
                                                        "Usuario eliminado correctamente", null));
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse("error", HttpStatus.NOT_FOUND.value(), "Usuario no encontrado",
                                                null));
        }
}