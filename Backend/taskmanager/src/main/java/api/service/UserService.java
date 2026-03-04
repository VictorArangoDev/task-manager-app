package api.service;

import org.springframework.stereotype.Service;

import api.exception.UserNotFoundException;
import api.dto.LoginRequest;
import api.dto.LoginResponse;
import api.exception.InvalidCredentialsException;
import api.model.Role;
import api.model.User;
import api.repository.RoleRepository;
import api.repository.UserRepository;
import api.utils.JwtUtil;
import java.util.List;
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

    public User crearUsuario(User user) {
        if (user.getRole() == null || user.getRole().getId() == null) {
            throw new RuntimeException("El rol es obligatorio");
        }

        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        user.setRole(role);
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

        try {

            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                throw new InvalidCredentialsException("Credenciales inválidas");
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                throw new InvalidCredentialsException("Credenciales inválidas");
            }

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> {
                        System.out.println("Usuario no encontrado: " + request.getUsername());
                        return new UserNotFoundException("Usuario no encontrado");
                    });

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                System.out.println("Contraseña incorrecta para: " + request.getUsername());
                throw new InvalidCredentialsException("Credenciales inválidas");
            }

            System.out.println("Login exitoso para: " + user.getUsername());

            String roleName = null;

            if (user.getRole() != null) {
                roleName = user.getRole().getName();
                System.out.println("Rol encontrado: " + roleName);
            } else {
                System.out.println("⚠ ERROR: El usuario no tiene rol asignado");
                throw new RuntimeException("Usuario sin rol asignado");
            }

            String token = jwtUtil.generateToken(user.getUsername(), roleName);
            System.out.println("Token generado correctamente");

            return new LoginResponse(
                    token,
                    user.getUsername(),
                    roleName);

        } catch (Exception e) {

            throw new RuntimeException("Error interno en el servidor");
        }
    }
}