package be.mathiasbosman.vim.security;

import java.time.LocalDateTime;
import java.util.UUID;

public record TokenRecord(String accessToken,
                          LocalDateTime expiresAt,
                          String username,
                          UUID refreshToken,
                          String tokenType) {

}
