package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.CategoryRecord;
import be.mathiasbosman.vim.domain.ItemRecord;
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
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/rest/items")
  public List<ItemRecord> getItems(@RequestParam ItemStatus status) {
    return itemService.findItems(status).stream().map(ItemRecord::fromEntity).toList();
  }

  @GetMapping("/rest/item/find")
  public ItemRecord findItem(@RequestParam UUID uuid) {
    return itemService.findById(uuid).map(ItemRecord::fromEntity).orElse(null);
  }

  @PostMapping("/rest/item")
  public ItemRecord saveItem(@RequestBody ItemRecord itemRecord) {
    return ItemRecord.fromEntity(itemService.saveItem(itemRecord.mapToItemEntity()));
  }

  @PutMapping("/rest/item")
  public ItemRecord updateItem(@RequestBody ItemRecord itemRecord) {
    CategoryRecord categoryRecord = itemRecord.categoryRecord();
    Item item = itemService.updateItem(itemRecord.id(), itemRecord.name(), itemRecord.brand(),
        categoryRecord != null ? categoryRecord.code() : null);
    return ItemRecord.fromEntity(item);
  }
}