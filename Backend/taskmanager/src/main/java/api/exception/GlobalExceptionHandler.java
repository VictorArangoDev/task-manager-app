package api.exception;

import java.util.HashMap;
import java.util.Map;

import javax.management.relation.RoleNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;

import api.dto.ApiResponse;

//significa 
// Intercepta todas las excepciones lanzadas en los controllers.
@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        // Maneja errores de credenciales incorrectas en el login.
        // Se lanza cuando el email o password no coinciden.
        // Devuelve 401 Unauthorized.
        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<ApiResponse> handleInvalidCredentials(InvalidCredentialsException e) {

                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(new ApiResponse(
                                                "Error",
                                                HttpStatus.UNAUTHORIZED.value(),
                                                "Credenciales inválidas",
                                                null));
        }

        // Maneja errores cuando el usuario no es encontrado en la BD.
        // Se mantiene con 401 para no revelar si el usuario existe o no.
        // Devuelve 401 Unauthorized.
        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ApiResponse> handleUserNotFound(UserNotFoundException e) {

                // Logueamos el mensaje real para nosotros
                logger.error("Error de login: " + e.getMessage());
                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(new ApiResponse(
                                                "Error",
                                                HttpStatus.UNAUTHORIZED.value(),
                                                "Credenciales inválidas",
                                                null));
        }

        // Maneja errores cuando cualquier recurso no es encontrado en la BD.
        // Ejemplo: prioridad, rol, tarea, proyecto no encontrado.
        // Devuelve 404 Not Found con el mensaje descriptivo del recurso.
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ApiResponse("error", 404, e.getMessage(), null));
        }

        // Maneja cualquier excepción no controlada por los handlers anteriores.
        // Es el último recurso — captura errores inesperados del servidor.
        // Devuelve 500 Internal Server Error.
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse> handleGenericException(Exception e) {

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ApiResponse(
                                                "Error",
                                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                "Error interno del servidor",
                                                null));
        }

        // Maneja los errores de validación de campos enviados en el request.
        // Se activa cuando las anotaciones como @Email, @NotBlank, @Size, etc. fallan.
        // Recorre cada campo con error y devuelve un mapa con: campo -> mensaje de
        // error.
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException e) {

                Map<String, String> errors = new HashMap<>();

                e.getBindingResult().getFieldErrors().forEach(error -> {
                        errors.put(error.getField(), error.getDefaultMessage());
                });

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse(
                                                "Error",
                                                HttpStatus.BAD_REQUEST.value(),
                                                "Errores de validación",
                                                errors));
        }

        // Maneja errores de integridad de la base de datos.
        // Se lanza cuando se viola una restricción como UNIQUE o NOT NULL.
        // Ejemplo: intentar registrar un usuario con un email que ya existe.
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ApiResponse> handleDuplicateKey(DataIntegrityViolationException e) {
                logger.error("Violación de integridad: " + e.getMostSpecificCause().getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiResponse("error", HttpStatus.CONFLICT.value(),
                                                "Ya existe un registro con esos datos", null));
        }

        // Maneja errores cuando se intenta crear un recurso que ya existe.
        // Se lanza manualmente desde los services antes de intentar guardar.
        // Ejemplo: prioridad, rol o proyecto con nombre duplicado.
        // Devuelve 409 Conflict con el mensaje descriptivo del recurso.
        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ApiResponse> handleDuplicate(DuplicateResourceException e) {
                Object data = e.getErrors() != null ? e.getErrors() : null;
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ApiResponse("error", 409, "Ya existen registros con esos datos", data));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiResponse> handleIllegalArgument(IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse("error", 400, e.getMessage(), null));
        }
}