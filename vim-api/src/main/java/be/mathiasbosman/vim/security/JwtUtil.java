package be.mathiasbosman.vim.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.Serializable;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

@UtilityClass
public class JwtUtil implements Serializable {

  SecretKey key = Jwts.SIG.HS256.key().build();

  public static String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public static LocalDateTime getExpirationDateFromToken(String token) {
    Date claimFromToken = getClaimFromToken(token, Claims::getExpiration);
    return claimFromToken.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private static Claims getAllClaimsFromToken(String token) {
    return Jwts.parser()
               .verifyWith(key)
               .clockSkewSeconds(10)
               .build()
               .parseSignedClaims(token)
               .getPayload();
  }

  public static boolean isTokenExpired(String token) {
    final LocalDateTime expiration = getExpirationDateFromToken(token);
    return expiration.isBefore(LocalDateTime.now());
  }

  public static String generateToken(UserDetails userDetails, LocalDateTime expiresAt) {
    Date issuedAtDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    Date expiresAtDate = Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant());
    return Jwts.builder()
        .claims(Collections.emptyMap())
        .subject(userDetails.getUsername())
        .issuedAt(issuedAtDate)
        .expiration(expiresAtDate)
        .signWith(key)
        .compact();
  }

  public static boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

}
