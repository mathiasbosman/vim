package be.mathiasbosman.vim.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Abstract controller containing a trace log for URI calls.
 */
@Slf4j
public abstract class AbstractVimController {

  @ModelAttribute
  public void interceptor(HttpServletRequest request) {
    log.trace("URI {} called by {}", request.getRequestURI(), request.getRemoteAddr());
  }
}
