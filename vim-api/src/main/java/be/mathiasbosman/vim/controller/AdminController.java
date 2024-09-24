package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.ServerInfoRecord;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Administration controller.
 */
@RestController
@RequestMapping(AdminController.REQUEST_MAPPING)
public class AdminController {

  public static final String REQUEST_MAPPING = "/admin";

  @GetMapping("/info")
  public ServerInfoRecord info() {
    return new ServerInfoRecord(LocalDateTime.now(), ZoneId.systemDefault(), true);
  }

}
