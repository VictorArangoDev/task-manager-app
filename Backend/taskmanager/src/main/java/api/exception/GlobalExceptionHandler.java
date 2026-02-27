package api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import api.dto.ApiResponse;



@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(InvalidCredentialsException.class)
   public ResponseEntity<ApiResponse> handleInvalidCredentials(InvalidCredentialsException e){

   return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ApiResponse(
                "Error", 
                HttpStatus.UNAUTHORIZED.value(), 
                "Credenciales inválidas", 
                null
            ));
   }
   
     @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(UserNotFoundException e) {

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ApiResponse(
                "Error", 
                HttpStatus.UNAUTHORIZED.value(), 
                "Credenciales inválidas", 
                null
            ));
    }
   
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception e) {
        
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse(
                "Error", 
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "Error interno del servidor", 
                null
            ));
    }

}