package be.mathiasbosman.vim.entity;

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
