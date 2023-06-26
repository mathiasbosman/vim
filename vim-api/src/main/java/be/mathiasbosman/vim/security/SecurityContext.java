package be.mathiasbosman.vim.security;

public final class SecurityContext {

  private SecurityContext() {
    throw new IllegalStateException("Utility class");
  }

  public static final class Role {

    public static final String PREFIX = "ROLE_";

    public static final String ADMIN = "admin";
    public static final String USER = "user";

    private Role() {
      throw new IllegalStateException("Utility class");
    }

  }
}
