package be.mathiasbosman.vim.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

public class AdminControllerTest extends AbstractControllerTest {

  @Test
  void info() throws Exception {
    String info = mapRequest("rest/admin/info");
    mvc().perform(get(info))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.time").isNotEmpty())
        .andExpect(jsonPath("$.running").value(Boolean.TRUE));
  }
}
