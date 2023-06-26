package be.mathiasbosman.vim.security;

import be.mathiasbosman.vim.domain.RefreshToken;
import be.mathiasbosman.vim.domain.User;
import be.mathiasbosman.vim.domain.security.Role;
import be.mathiasbosman.vim.repository.RefreshTokenRepository;
import be.mathiasbosman.vim.repository.RoleRepository;
import be.mathiasbosman.vim.repository.UserRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private static final TemporalAmount JWT_TOKEN_VALIDITY = Duration.ofHours(1);
  private static final TemporalAmount REFRESH_TOKEN_VALIDITY = Duration.ofHours(2);

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final PasswordEncoder passwordEncoder;


  @Transactional
  public TokenRecord authenticate(@NonNull String username, @NonNull String password) {
    try {
      Authentication auth = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
      if (auth.isAuthenticated()) {
        return createToken(username);
      } else {
        throw new AccessDeniedException("Invalid credentials");
      }
    } catch (DisabledException e) {
      throw new AccessDeniedException("User is disabled", e);
    } catch (Exception e) {
      throw new IllegalStateException("Exception while authenticating", e);
    }
  }

  @Transactional
  public TokenRecord register(@NonNull String username, @NonNull String password) {
    Role defaultRole = roleRepository.getByName(SecurityContext.Role.USER);
    User user = User.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .roles(Collections.singletonList(defaultRole))
        .build();
    userRepository.save(user);
    return createToken(username);
  }

  @Transactional
  public TokenRecord createToken(User user) {
    return this.createToken(user.getUsername());
  }

  private TokenRecord createToken(String username) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    LocalDateTime expiresAt = LocalDateTime.now().plus(JWT_TOKEN_VALIDITY);
    String token = JwtTokenUtil.generateToken(userDetails, expiresAt);
    log.trace("Created token {} for user {}", token, username);
    return userRepository.getByUsername(userDetails.getUsername())
        .map(user -> {
          RefreshToken refreshToken = getRefreshToken(user);
          return new TokenRecord(
              token,
              expiresAt,
              username,
              refreshToken.getToken(),
              JwtRequestFilter.HEADER_AUTHORIZATION_TYPE
          );
        }).orElseThrow(
            () -> new IllegalStateException("Could not create token for user " + username));
  }

  private RefreshToken getRefreshToken(User user) {
    return refreshTokenRepository.findByUser(user)
        .orElseGet(() -> refreshTokenRepository.save(RefreshToken.builder()
            .user(user)
            .token(UUID.randomUUID())
            .expiresAt(Instant.now().plus(REFRESH_TOKEN_VALIDITY))
            .build()));
  }

  public Optional<RefreshToken> findRefreshTokenById(@NonNull UUID refreshTokenId) {
    return refreshTokenRepository.findByToken(refreshTokenId);
  }

  public RefreshToken verifyRefreshToken(RefreshToken refreshToken) {
    if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
      log.trace("Refresh token {} expired, deleting without transaction", refreshToken.getId());
      refreshTokenRepository.delete(refreshToken);
      throw new IllegalStateException("Refresh token has expired");
    }
    return refreshToken;
  }

}
