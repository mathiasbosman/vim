package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.db.ItemRepository;
import be.mathiasbosman.vim.entity.Category;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link ItemService} interface.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  @Override
  @Transactional(readOnly = true)
  public Optional<Item> findById(UUID id) {
    return itemRepository.findById(id);
  }

  @Override
  @PreAuthorize("hasAuthority('item-write')")
  @Transactional
  public Item saveItem(Item item) {
    return itemRepository.save(item);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Item> findItems(Category category) {
    return itemRepository.findAllByCategory(category);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Item> findItems(ItemStatus status) {
    return itemRepository.findAllByStatus(status);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Item> searchItems(String name, List<Category> categories, List<ItemStatus> statuses) {
    return itemRepository.searchItems(name, categories, statuses);
  }
}