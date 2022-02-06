package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.entity.Category;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface holding mostly {@link Item} related methods.
 */
public interface ItemService {

  Optional<Item> findById(UUID id);

  Item saveItem(Item item);

  List<Item> findItems(Category category);

  List<Item> findItems(ItemStatus status);

  List<Item> searchItems(String name, List<Category> categories, List<ItemStatus> statuses);
}
