package test.darum.authservice.service;

import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import test.darum.authservice.dtos.LoginRequestDto;
import test.darum.authservice.dtos.RegisterRequestDto;
import test.darum.authservice.model.User;
import test.darum.authservice.utils.JwtUtil;

import java.util.Optional;

@Service
public class AuthService {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Transactional
  public boolean registerUser(RegisterRequestDto requestDto) {

    // Check if user already exists
    if (userService.findByEmail(requestDto.getEmail()).isPresent()) {
      return false;
    }
    
    String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

    User newUser = new User();
    newUser.setEmail(requestDto.getEmail());
    newUser.setPassword(encodedPassword);
    newUser.setRole(requestDto.getRole());
    
    userService.createUser(newUser);

    return true;
  }
  public Optional<String> authenticate(LoginRequestDto loginRequestDto) {

    return userService.findByEmail(loginRequestDto.getEmail())
                      .filter(u -> passwordEncoder.matches(loginRequestDto.getPassword(), u.getPassword()))
                      .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole().toString()));
  }

  public boolean validateToken(String token) {
    try {
      jwtUtil.validateToken(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
