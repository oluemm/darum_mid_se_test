package test.darum.authservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import test.darum.authservice.model.User;
import test.darum.authservice.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

  private final Logger log;
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.log = LoggerFactory.getLogger(this.getClass());
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public void createUser(User newUser) {
    log.info("Creating user {}", newUser.getEmail());
    try {
      userRepository.save(newUser);
    }catch (Exception e) {
      log.error("Error while saving user {}", newUser.getEmail(), e);
    }
  }
}
