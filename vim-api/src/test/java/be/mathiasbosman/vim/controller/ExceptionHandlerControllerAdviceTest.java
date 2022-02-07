package be.mathiasbosman.vim.controller;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.mathiasbosman.vim.domain.VimException;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ExceptionHandlerControllerAdviceTest extends AbstractControllerTest {

  @MockBean
  private ExceptionServiceStub exceptionServiceStub;

  @Test
  void testRunTimeException() throws Exception {

    when(exceptionServiceStub.throwException())
        .thenThrow(new RuntimeException("foo bar"));

    mvc.perform(get("/exceptionStub/throw"))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
        .andExpect(jsonPath("$.message").value("foo bar"))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.exception").exists())
        .andExpect(jsonPath("$.errorId").exists());
  }

  @Test
  void testVimException() throws Exception {
    for (Level logLevel : Level.values()) {
      reset(exceptionServiceStub);
      when(exceptionServiceStub.throwException()).thenThrow(
          new VimException(logLevel, logLevel.name()));
      mvc.perform(get("/exceptionStub/throw"))
          .andExpect(status().isBadRequest())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
          .andExpect(jsonPath("$.message").value(logLevel.name()))
          .andExpect(jsonPath("$.timestamp").exists())
          .andExpect(jsonPath("$.error").exists())
          .andExpect(jsonPath("$.exception").exists())
          .andExpect(jsonPath("$.errorId").exists());
    }
  }
}
