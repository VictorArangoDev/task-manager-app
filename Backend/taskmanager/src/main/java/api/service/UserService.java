package api.service;

import org.springframework.stereotype.Service;

import api.exception.UserNotFoundException;
import api.dto.LoginRequest;
import api.dto.LoginResponse;
import api.dto.RegisterRequest;
import api.dto.UserResponse;
import api.exception.DuplicateResourceException;
import api.exception.InvalidCredentialsException;
import api.exception.ResourceNotFoundException;
import api.model.Role;
import api.model.User;
import api.repository.RoleRepository;
import api.repository.UserRepository;
import api.utils.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    public User crearUsuario(RegisterRequest request) {

    Map<String, String> errors = new HashMap<>();

    if (userRepository.existsByEmail(request.getEmail())) {
        errors.put("email", "Este email ya está registrado");
    }

    if (userRepository.existsByUsername(request.getUsername())) {
        errors.put("username", "Este username ya está en uso");
    }

    if (userRepository.existsByDocument(request.getDocument())) {
        errors.put("document", "Este documento ya está registrado");
    }

    if (!errors.isEmpty()) {
        throw new DuplicateResourceException(errors);
    }

    // buscar rol USER automáticamente
    Role role = roleRepository.findByName("MEMBER")
            .orElseThrow(() -> new ResourceNotFoundException("Rol USER no encontrado"));

    User user = new User();

    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());
    user.setName(request.getName());
    user.setDocument(request.getDocument());
    user.setRole(role);

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

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        String email = request.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("La contraseña no coincide");
        }

        if (user.getRole() == null) {
            throw new RuntimeException("Usuario sin rol asignado");
        }

        String roleName = user.getRole().getName();
        String token = jwtUtil.generateToken(user.getUsername(), roleName);

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
                user.getUpdatedAt());

        return new LoginResponse(userResponse, token);
    }

    public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
}
}