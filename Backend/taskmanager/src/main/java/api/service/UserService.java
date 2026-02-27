package api.service;
import org.springframework.stereotype.Service;

import api.dto.LoginRequest;
import api.dto.LoginResponse;
import api.exception.InvalidCredentialsException;
import api.model.User;
import api.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User crearUsuario(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> finAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> update(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userDetails.getUsername() != null && !userDetails.getUsername().trim().isEmpty()) {
                        user.setUsername(userDetails.getUsername());
                    }
                    if (userDetails.getEmail() != null && !userDetails.getEmail().trim().isEmpty()) {
                        user.setEmail(userDetails.getEmail());
                    }
                    if (userDetails.getPassword() != null && !userDetails.getPassword().trim().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    if (userDetails.getRole() != null) {
                        user.setRole(userDetails.getRole());
                    }
                   
                    return userRepository.save(user);
                });
    }

    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }


 public LoginResponse login(LoginRequest request) {

    if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
        
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
       try {
          
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> {
                       
                        throw new UserNotFoundException("Usuario no encontrado");
                    });
            
           
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
               
                throw new InvalidCredentialsException("Credenciales inválidas");
            }
            
            
           
            String token = generateSecureToken(user);
            
            return new LoginResponse(
                token, 
                user.getUsername(), 
                user.getRole().toString()
            );
            
        } catch (UserNotFoundException e) {
            
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
    }
    
    // Método privado para generar token (mejorar después con JWT)
    private String generateSecureToken(User user) {
        // TODO: Implementar JWT real
        return "token-temporal-" + System.currentTimeMillis() + "-" + user.getId();
    }
    

}