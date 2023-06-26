package be.mathiasbosman.vim.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  public static final String HEADER_AUTHORIZATION_TYPE = "Bearer";
  private static final String HEADER_AUTHORIZATION_KEY = "Authorization";
  private final VimUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain chain) throws ServletException, IOException {

    getToken(request).ifPresent(token -> loadAuthentication(request, token));
    chain.doFilter(request, response);
  }

  private Optional<String> getToken(HttpServletRequest request) {
    String header = request.getHeader(HEADER_AUTHORIZATION_KEY);
    if (header == null || !header.startsWith(HEADER_AUTHORIZATION_TYPE)) {
      return Optional.empty();
    }

    return Optional.of(header.substring(HEADER_AUTHORIZATION_TYPE.length() + 1));
  }

  private void loadAuthentication(HttpServletRequest request, String jwtToken) {
    String username = JwtTokenUtil.getUsernameFromToken(jwtToken);
    if (!StringUtils.hasLength(username)) {
      log.warn("Username '{}' has no length", username);
      return;
    }
    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      log.trace("Security context already has authentication");
      return;
    }
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (!JwtTokenUtil.validateToken(jwtToken, userDetails)) {
      log.warn("Jwt token invalid");
      return;
    }
    UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
        new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
    usernamePasswordAuthToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));
    log.trace("Setting authentication context with token {}", usernamePasswordAuthToken);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
  }
}
