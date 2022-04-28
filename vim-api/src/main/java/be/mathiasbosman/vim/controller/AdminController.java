package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.ServerInfoDto;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Administration controller.
 */
@RestController
public class AdminController {

  @GetMapping("/rest/public/admin/info")
  public ServerInfoDto info() {
    return new ServerInfoDto(LocalDateTime.now(), true);
  }
}
