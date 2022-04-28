package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.CategoryDto;
import be.mathiasbosman.vim.domain.ItemDto;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.service.ItemService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller mostly used with the {@link ItemService}.
 */
@RestController
@RequiredArgsConstructor
public class ItemController extends AbstractVimController {

  private final ItemService itemService;

  @GetMapping("/rest/items")
  public List<ItemDto> getItems(@RequestParam ItemStatus status) {
    return itemService.findItems(status).stream().map(ItemDto::fromEntity).toList();
  }

  @GetMapping("/rest/item/find")
  public ItemDto findItem(@RequestParam UUID uuid) {
    return itemService.findById(uuid).map(ItemDto::fromEntity).orElse(null);
  }

  @PostMapping("/rest/item")
  public ItemDto saveItem(@RequestBody ItemDto itemDto) {
    return ItemDto.fromEntity(itemService.saveItem(itemDto.mapToItemEntity()));
  }

  @PutMapping("/rest/item")
  public ItemDto updateItem(@RequestBody ItemDto itemDto) {
    CategoryDto categoryDto = itemDto.categoryDto();
    Item item = itemService.updateItem(itemDto.id(), itemDto.name(), itemDto.brand(),
        categoryDto != null ? categoryDto.code() : null);
    return ItemDto.fromEntity(item);
  }
}