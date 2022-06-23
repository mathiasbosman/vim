package be.mathiasbosman.vim.domain;

/**
 * Possible states of an {@link Item}.
 */
public enum ItemStatus {
  AVAILABLE,
  UNAVAILABLE,
  RESERVED,
  CHECKED_OUT,
  DAMAGED
}
