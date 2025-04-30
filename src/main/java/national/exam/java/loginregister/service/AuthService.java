package national.exam.java.loginregister.service;

import national.exam.java.loginregister.dto.LoginRequest;
import national.exam.java.loginregister.dto.RegisterRequest;
import national.exam.java.loginregister.entity.User;
import national.exam.java.loginregister.repository.UserRepository;
import national.exam.java.loginregister.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest registerRequest) {
//        if (userRepository.existsByUsername(registerRequest.getUsername())){
//            return "Username already exists";
//        }
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return "Username already exists";
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);
        return "User registered successfully";
    }
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(user.getUsername());
    }
}
