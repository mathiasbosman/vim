package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.dto.ServerInfoDto;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController extends AbstractVimController {

  @GetMapping("/rest/admin/info")
  public ServerInfoDto info() {
    return new ServerInfoDto(LocalDateTime.now(), true);
  }
}
