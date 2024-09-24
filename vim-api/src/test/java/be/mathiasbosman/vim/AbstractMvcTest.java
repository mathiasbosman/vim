package be.mathiasbosman.vim;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@AutoConfigureMockMvc
public abstract class AbstractMvcTest extends AbstractSpringBootTest {

  private final ObjectMapper mapper = new ObjectMapper();
  @Autowired
  protected MockMvc mvc;

  protected MockHttpServletRequestBuilder postObject(String url, Object object) throws Exception {

    MockHttpServletRequestBuilder requestBuilder = post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(object));
    return requestBuilder.with(csrf());
  }

  protected MockHttpServletRequestBuilder putObject(String url, Object object) throws Exception {

    MockHttpServletRequestBuilder requestBuilder = put(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(object));
    return requestBuilder.with(csrf());
  }
}
