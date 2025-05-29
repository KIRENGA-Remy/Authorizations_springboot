package national.exam.java.loginregister.controller;

import national.exam.java.loginregister.dto.LoginRequest;
import national.exam.java.loginregister.dto.RegisterRequest;
import national.exam.java.loginregister.entity.User;
import national.exam.java.loginregister.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful");
        response.put("user", mapUserToResponse(user));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("user", mapUserToResponse(user));
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> mapUserToResponse(User user) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("firstName", user.getFirstName());
        userResponse.put("lastName", user.getLastName());
        userResponse.put("email", user.getEmail());
        userResponse.put("role", user.getRole());
        return userResponse;
    }
}