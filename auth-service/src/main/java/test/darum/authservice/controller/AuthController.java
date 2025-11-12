package test.darum.authservice.controller;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.darum.authservice.dtos.AuthClaims;
import test.darum.authservice.dtos.LoginRequestDto;
import test.darum.authservice.dtos.LoginResponseDto;
import test.darum.authservice.dtos.RegisterRequestDto;
import test.darum.authservice.service.AuthService;

@RestController
@Tag(name = "Authentication Controller", description = "Create, Login and Validate tokens")
@RequestMapping("auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @Operation(summary = "Register a new user")
  @PostMapping("/register")
  public ResponseEntity<Void> register(
      @RequestBody RegisterRequestDto registrationRequestDto) {

    boolean success = authService.registerUser(registrationRequestDto);

    if (success) {
      // User created successfully
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      // User already exists (email conflict)
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @Operation(summary = "Generate token on user login")
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(
      @RequestBody LoginRequestDto loginRequestDto) {

    Optional<String> tokenOptional = authService.authenticate(loginRequestDto);

    if (tokenOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = tokenOptional.get();
    return ResponseEntity.ok(new LoginResponseDto(token));
  }


  @Operation(summary = "Validate Token")
  @GetMapping("/validate")
  public ResponseEntity<AuthClaims> validateToken(
      @RequestHeader(value = "Authorization", required = false) String authHeader) {

    // Authorization: Bearer <token>
    if(authHeader == null || !authHeader.toLowerCase().startsWith("bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    var claimsData = authService.validateToken(authHeader.split(" ")[1]);
    return claimsData.isValid()
           ? ResponseEntity.ok(claimsData)
           : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
