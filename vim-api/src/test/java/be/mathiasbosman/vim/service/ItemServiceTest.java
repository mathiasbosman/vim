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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ItemServiceTest extends AbstractServiceTest {

  @InjectMocks
  ItemService itemService;

  @Mock
  ItemRepository itemRepository;

  @Test
  void saveItem() {
    Category category = Category.builder().name("Category A").build();
    Item mockItem = Item.builder()
        .name("foo")
        .brand("ba")
        .category(category)
        .status(ItemStatus.AVAILABLE).build();
    when(itemRepository.save(any(Item.class))).thenReturn(mockItem);
    Item savedItem = itemService.saveItem(mockItem);
    assertThat(savedItem).isEqualTo(mockItem);
    assertThat(savedItem.getId()).isEqualTo(mockItem.getId());
    assertThat(savedItem.getName()).isEqualTo(mockItem.getName());
    assertThat(savedItem.getBrand()).isEqualTo(mockItem.getBrand());
    assertThat(savedItem.getStatus()).isEqualTo(mockItem.getStatus());
    assertThat(savedItem.getCategory().getId()).isEqualTo(category.getId());
  }

  @Test
  void findItems() {
    Item item1 = Item.builder().name("Item A").build();
    Item item2 = Item.builder().name("Item B").build();
    when(itemRepository.findAllByStatus(ItemStatus.AVAILABLE))
        .thenReturn(List.of(item1, item2));
    assertThat(itemService.findItems(ItemStatus.AVAILABLE))
        .containsExactly(item1, item2);
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