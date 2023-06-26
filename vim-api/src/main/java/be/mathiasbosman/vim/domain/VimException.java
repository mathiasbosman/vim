package be.mathiasbosman.vim.domain;

import org.slf4j.event.Level;

/**
 * Exception used for validations.
 */
public class VimException extends RuntimeException implements ValidationException {

  private final Level logLevel;

  public VimException(Level logLevel, String message, Object... args) {
    super(String.format(message, args));
    this.logLevel = logLevel;
  }

  @Override
  public Level getLogLevel() {
    return logLevel;
  }
}
