package com.ptit.graduation.service.user;


import com.ptit.graduation.repository.user.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserMongoRepository userRepository;
  @Autowired
  public CustomUserDetailsService(UserMongoRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findByUsername(username);
//          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return User.builder()
          .username(user.getUsername())
          .password(user.getPassword())
          .roles(user.getRole().name())
          .build();
  }
}