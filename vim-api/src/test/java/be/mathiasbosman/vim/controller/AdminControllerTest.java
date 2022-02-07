package be.mathiasbosman.vim.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class AdminControllerTest extends AbstractControllerTest {

  @Test
  void info() throws Exception {
    mvc.perform(get("/rest/admin/info"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.time").isNotEmpty())
        .andExpect(jsonPath("$.running").value(Boolean.TRUE));
  }
}
