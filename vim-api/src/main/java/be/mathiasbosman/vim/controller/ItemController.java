package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.dto.ItemDto;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.service.ItemServiceImpl;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController extends AbstractVimController {

  private final ItemServiceImpl itemService;

  @GetMapping("/rest/items")
  public List<ItemDto> getItems(@RequestParam ItemStatus status) {
    return itemService.findItems(status).stream().map(ItemDto::fromEntity).toList();
  }

  @GetMapping("/rest/item/find")
  public ItemDto findItem(@RequestParam UUID uuid) {
    return itemService.findById(uuid).map(ItemDto::fromEntity).orElse(null);
  }
}