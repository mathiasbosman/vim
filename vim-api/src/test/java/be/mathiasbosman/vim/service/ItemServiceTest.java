package be.mathiasbosman.vim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import be.mathiasbosman.vim.db.ItemRepository;
import be.mathiasbosman.vim.entity.Category;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ItemServiceTest extends AbstractServiceTest {

  @InjectMocks
  ItemServiceImpl itemService;

  @Mock
  ItemRepository itemRepository;

  @Test
  void findItem() {
    UUID id = UUID.randomUUID();
    Item item = Item.builder().id(id).name("Item A").build();

    when(itemRepository.findById(id)).thenReturn(Optional.of(item));

    assertThat(itemService.findById(id)).hasValue(item);
  }

  @Test
  void findItemsByStatus() {
    Item item1 = Item.builder().name("Item A").build();
    Item item2 = Item.builder().name("Item B").build();

    when(itemRepository.findAllByStatus(ItemStatus.AVAILABLE))
        .thenReturn(List.of(item1, item2));

    assertThat(itemService.findItems(ItemStatus.AVAILABLE))
        .containsExactly(item1, item2);
  }

  @Test
  void findItemsByCategory() {
    Item item1 = Item.builder().name("Item A").build();
    Item item2 = Item.builder().name("Item B").build();

    when(itemRepository.findAllByCategory(any(Category.class))).thenReturn(List.of(item1, item2));

    assertThat(itemService.findItems(Category.builder().name("Category A").build()))
        .containsExactly(item1, item2);
  }

  @Test
  void searchItems() {
    Item item1 = Item.builder().name("Item A").build();
    Item item2 = Item.builder().name("Item B").build();

    when(itemRepository.searchItems(anyString(), any(), any()))
        .thenReturn(List.of(item1, item2));

    assertThat(itemService.searchItems("Item", null, null))
        .containsExactlyInAnyOrder(item1, item2);
  }

}