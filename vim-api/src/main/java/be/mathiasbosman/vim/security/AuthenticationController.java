package be.mathiasbosman.vim.security;

import be.mathiasbosman.vim.domain.RefreshToken;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AuthenticationController.REQUEST_MAPPING_PREFIX)
public class AuthenticationController {

  public static final String REQUEST_MAPPING_PREFIX = "/auth";
  public static final String REFRESH_TOKEN_MAPPING = "/refreshToken";

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<TokenRecord> loginUser(
      @RequestParam("username") String username,
      @RequestParam("password") String password) {
    TokenRecord token = authenticationService.authenticate(username, password);
    return ResponseEntity.ok(token);
  }

  @PostMapping(REFRESH_TOKEN_MAPPING)
  public ResponseEntity<TokenRecord> refreshToken(
      @RequestParam("refreshToken") UUID refreshTokenId) {
    TokenRecord token = authenticationService.findRefreshTokenById(refreshTokenId)
        .map(authenticationService::verifyRefreshToken)
        .map(RefreshToken::getUser)
        .map(authenticationService::createToken)
        .orElseThrow(() -> new IllegalStateException("Refresh token does not exist"));
    return ResponseEntity.ok(token);
  }


  @PostMapping("/register")
  public ResponseEntity<TokenRecord> registerUser(
      @RequestParam("username") String username,
      @RequestParam("password") String password) {

    TokenRecord token = authenticationService.register(username, password);
    return ResponseEntity.accepted().body(token);
  }
}
