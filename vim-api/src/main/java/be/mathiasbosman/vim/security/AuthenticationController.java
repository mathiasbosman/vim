package be.mathiasbosman.vim.security;

import be.mathiasbosman.vim.domain.RefreshToken;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AuthenticationController.REQUEST_MAPPING)
public class AuthenticationController {

  public static final String REQUEST_MAPPING = "/auth";
  public static final String REFRESH_TOKEN_MAPPING = "/refreshToken";

  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public TokenRecord loginUser(
      @RequestParam("username") String username,
      @RequestParam("password") String password) {
    return authenticationService.authenticate(username, password);
  }

  @PostMapping(REFRESH_TOKEN_MAPPING)
  public TokenRecord refreshToken(
      @RequestParam("refreshToken") UUID refreshTokenId) {
      return authenticationService.findRefreshTokenById(refreshTokenId)
                                .map(authenticationService::verifyRefreshToken)
                                .map(RefreshToken::getUser)
                                .map(authenticationService::createToken)
                                .orElseThrow(() -> new IllegalStateException("Refresh token does not exist"));
  }


  @PostMapping("/register")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public TokenRecord registerUser(
      @RequestParam("username") String username,
      @RequestParam("password") String password) {

      return authenticationService.register(username, password);
  }
}
