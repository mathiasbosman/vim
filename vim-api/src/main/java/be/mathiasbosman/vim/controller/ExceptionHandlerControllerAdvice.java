package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.VimException;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<Problem> handleRuntimeException(HttpServletRequest request,
      RuntimeException ex) {
    return createAndLogProblem(request, HttpStatus.INTERNAL_SERVER_ERROR, ex, Level.ERROR);
  }

  @ExceptionHandler(VimException.class)
  public ResponseEntity<Problem> handleVimException(HttpServletRequest request, VimException ex) {
    return createAndLogProblem(request, HttpStatus.BAD_REQUEST, ex, ex.getLogLevel());
  }

  private ResponseEntity<Problem> createAndLogProblem(HttpServletRequest request,
      HttpStatus httpStatus, Throwable exception, Level logLevel) {
    final String logPl = "Error with ID: {} - {} - {}";
    final UUID id = UUID.randomUUID();
    final String message = exception.getMessage();
    final String requestURI = request.getRequestURI();
    switch (logLevel) {
      case WARN -> log.warn(logPl, id, message, requestURI, exception);
      case TRACE -> log.trace(logPl, id, message, requestURI, exception);
      case DEBUG -> log.debug(logPl, id, message, requestURI, exception);
      case INFO -> log.info(logPl, id, message, requestURI, exception);
      default -> log.error(logPl, id, message, requestURI, exception);
    }
    return createProblem(message, httpStatus, requestURI, id);
  }

  private ResponseEntity<Problem> createProblem(String title, HttpStatus status, String requestURI,
      UUID id) {
    Problem problem = Problem.create()
        .withProperties(Map.of(
            "requestUrl", requestURI,
            "errorId", id))
        .withStatus(status)
        .withTitle(title);

    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(problem);
  }
}
