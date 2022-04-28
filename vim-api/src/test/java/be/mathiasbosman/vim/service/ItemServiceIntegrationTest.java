package be.mathiasbosman.vim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import be.mathiasbosman.vim.AbstractSpringBootTest;
import be.mathiasbosman.vim.domain.ItemMother;
import be.mathiasbosman.vim.entity.Category;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.security.SecurityContext.Authority;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(authorities = {Authority.ITEM_WRITE})
class ItemServiceIntegrationTest extends AbstractSpringBootTest {

  @Autowired
  private ItemService itemService;


  @Test
  void saveItem() {
    Item item = ItemMother.availableItem();

    itemService.saveItem(item);

    assertThat(item.getId()).isNotNull();
    assertThat(item)
        .extracting(Item::getName, Item::getBrand, Item::getStatus)
        .containsExactly(item.getName(), item.getBrand(), item.getStatus());
  }

  @Test
  void updateItemOnNoneExistingItem() {
    assertThatThrownBy(
        () -> itemService.updateItem(UUID.randomUUID(), "foo", "bar", "cat"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void updateItemWithoutCategory() {
    Item item = ItemMother.availableItem();
    entityManager.persist(item);

    itemService.updateItem(item.getId(), "name", "brand", null);

    assertThat(item)
        .extracting(Item::getName, Item::getBrand, Item::getStatus, Item::getCategory)
        .containsExactly("name", "brand", item.getStatus(), item.getCategory());
  }

  @Test
  void updateItemWithCategory() {
    Item item = ItemMother.availableItem();
    entityManager.persist(item);
    Category category = Category.builder().name("foo bar").code("foo").build();
    entityManager.persist(category);

    itemService.updateItem(item.getId(), "name", "brand", "foo");

    assertThat(item)
        .extracting(Item::getName, Item::getBrand, Item::getStatus, Item::getCategory)
        .containsExactly("name", "brand", item.getStatus(), category);
  }

}
