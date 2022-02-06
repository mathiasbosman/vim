package be.mathiasbosman.vim.domain;

import org.slf4j.event.Level;

/**
 * Exception used for validations.
 */
public class VimException extends RuntimeException {

  private final Level logLevel;

  public VimException(Level logLevel, String message, Object... args) {
    super(String.format(message, args));
    this.logLevel = logLevel;
  }

  public Level getLogLevel() {
    return logLevel;
  }
}
