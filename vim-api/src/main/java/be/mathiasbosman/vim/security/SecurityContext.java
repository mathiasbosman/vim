package be.mathiasbosman.vim.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class SecurityContext {

  @UtilityClass
  public static final class Role {

    public static final String PREFIX = "ROLE_";

    public static final String ADMIN = "admin";
    public static final String USER = "user";

  }
}
