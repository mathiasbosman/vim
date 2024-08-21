package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.ServerInfoRecord;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Administration controller.
 */
@RestController
public class AdminController {

  @GetMapping("/admin/info")
  public ServerInfoRecord info() {
    return new ServerInfoRecord(LocalDateTime.now(), ZoneId.systemDefault(), true);
  }

}
