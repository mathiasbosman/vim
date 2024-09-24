package be.mathiasbosman.vim.security;

import be.mathiasbosman.vim.controller.TransactionController;
import be.mathiasbosman.vim.security.SecurityContext.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] publicPatterns = {
      AuthenticationController.REQUEST_MAPPING + "/**",
      "/admin/info/**"
  };
  private static final String[] userPatterns = {
      TransactionController.REQUEST_MAPPING + "/**"
  };
  private static final String[] adminPatterns = {
      "/admin"
  };
  private final JwtRequestFilter jwtRequestFilter;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // enable CORS and disable CSRF
    http.csrf(AbstractHttpConfigurer::disable);
    http.cors(Customizer.withDefaults());

    // set session management to stateless
    http.sessionManagement(
        config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    // set unauthorized request exception handler
    http.exceptionHandling(
        config -> config.authenticationEntryPoint(this::createAuthenticationExceptionHandler));

    // set endpoint permissions
    http.authorizeHttpRequests(requests -> requests.requestMatchers(publicPatterns).permitAll()
        .requestMatchers(userPatterns).hasRole(Role.USER)
        .requestMatchers(adminPatterns).hasRole(Role.ADMIN)
        .anyRequest().denyAll());

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  private void createAuthenticationExceptionHandler(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException authenticationException)
      throws IOException {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    Problem problem = Problem.create()
        .withStatus(status)
        .withTitle(status.getReasonPhrase());
    response.setStatus(status.value());
    response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(problem));
  }

  @Bean
  public CorsFilter corsFilter() {
    // Used by spring security if CORS is enabled.
    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
