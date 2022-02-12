package be.mathiasbosman.vim.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

  @Autowired
  protected MockMvc mvc;

  private final ObjectMapper mapper = new ObjectMapper();

  protected MockHttpServletRequestBuilder postObject(String url, Object object, boolean csrfToken)
      throws Exception {

    MockHttpServletRequestBuilder requestBuilder = post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(object));
    return csrfToken ? requestBuilder.with(csrf()) : requestBuilder;
  }
}
