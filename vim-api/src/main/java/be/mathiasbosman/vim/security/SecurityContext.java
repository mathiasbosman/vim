package be.mathiasbosman.vim.security;

public final class SecurityContext {

  private SecurityContext() {
    throw new IllegalStateException("Utility class");
  }

  public static final class Authority {

    public static final String API_USER = "api-user";
    public static final String ITEM_WRITE = "item-write";
    public static final String TRANSACTION_WRITE = "transaction-write";

    private Authority() {
      throw new IllegalStateException("Utility class");
    }

  }
}
