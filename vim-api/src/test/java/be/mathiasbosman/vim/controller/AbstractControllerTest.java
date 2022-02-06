package be.mathiasbosman.vim.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

  @Autowired
  private MockMvc mvc;

  protected MockMvc mvc() {
    return this.mvc;
  }

  public String mapRequest(Object... paths) {
    return Arrays.stream(paths)
        .map(Object::toString)
        .collect(Collectors.joining("/", "/", ""));
  }

  @Test
  void testMapRequest() {
    UUID testUuid = UUID.randomUUID();
    assertThat(mapRequest("string", testUuid, "string2", false))
        .isEqualTo("/string/" + testUuid + "/string2/false");
  }
}
