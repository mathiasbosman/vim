package be.mathiasbosman.vim.db;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.vim.entity.Category;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ItemRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private ItemRepository repository;

  @Test
  void findAllByNameContainingIgnoreCase() {
    assertThat(repository.findAllByNameContainingIgnoreCase("dummy")).isEmpty();
    Item itemA = create(Item.builder().name("The item A").build());
    Item itemB = create(Item.builder().name("The item B").build());
    assertThat(repository.findAllByNameContainingIgnoreCase("item"))
        .hasSize(2)
        .contains(itemA, itemB);
    assertThat(repository.findAllByNameContainingIgnoreCase("ITEM"))
        .hasSize(2)
        .contains(itemA, itemB);
  }

  @Test
  void findAllByCategory() {
    assertThat(repository.findAllByCategory(null)).isEmpty();
    Category categoryA = create(mockCategory("Category A", "A"));
    Category categoryB = create(mockCategory("Category B", "B"));
    Category categoryC = create(mockCategory("Category C", "C"));
    Item itemA = create(Item.builder().name("Item A").category(categoryA).build());
    Item itemB = create(Item.builder().name("Item B").category(categoryB).build());
    assertThat(repository.findAllByCategory(categoryA))
        .hasSize(1)
        .contains(itemA);
    assertThat(repository.findAllByCategory(categoryB))
        .hasSize(1)
        .contains(itemB);
    assertThat(repository.findAllByCategory(categoryC))
        .isEmpty();
  }

  @Test
  void findAllByStatus() {
    assertThat(repository.findAllByStatus(null)).isEmpty();
    Item itemA = create(Item.builder().name("Item A").status(ItemStatus.AVAILABLE).build());
    Item itemB = create(Item.builder().name("Item B").status(ItemStatus.AVAILABLE).build());
    create(Item.builder().name("Item C").status(ItemStatus.DAMAGED).build());
    assertThat(repository.findAllByStatus(ItemStatus.AVAILABLE))
        .containsExactlyInAnyOrder(itemA, itemB);
    assertThat(repository.findAllByStatus(ItemStatus.UNAVAILABLE)).isEmpty();
  }

  @Test
  void searchItems() {
    assertThat(repository.searchItems(null, null, null)).isEmpty();
    Category categoryA = create(mockCategory("Category A", "A"));
    Category categoryB = create(mockCategory("Category B", "B"));
    create(mockCategory("Category C", "C"));
    Item itemA = create(Item.builder().name("Item A").category(categoryA).build());
    Item itemB = create(Item.builder().name("Item B").category(categoryB).build());

    assertThat(repository.searchItems("item", null, null))
        .containsExactlyInAnyOrder(itemA, itemB);
  }

  private Category mockCategory(String name, String code) {
    return Category.builder().name(name).code(code).build();
  }
}
