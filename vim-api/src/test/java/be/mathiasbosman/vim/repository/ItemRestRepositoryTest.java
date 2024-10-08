package be.mathiasbosman.vim.repository;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.vim.domain.Category;
import be.mathiasbosman.vim.domain.CategoryMother;
import be.mathiasbosman.vim.domain.Item;
import be.mathiasbosman.vim.domain.ItemMother;
import be.mathiasbosman.vim.domain.ItemStatus;
import be.mathiasbosman.vim.security.SecurityContext.Role;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(roles = {Role.USER})
class ItemRestRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private ItemRestRepository repository;

  @Test
  void findAllByNameContainingIgnoreCaseIsEmpty() {
    assertThat(repository.findAllByNameContainingIgnoreCase("dummy")).isEmpty();
  }

  @Test
  void findAllByNameContainingIgnoreCase() {
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
  void findAllByCategoryIsEmpty() {
    assertThat(repository.findAllByCategory(null)).isEmpty();
  }

  @Test
  void findAllByCategory() {
    Category categoryA = create(CategoryMother.withNameAndCode("Category A", "A"));
    Category categoryB = create(CategoryMother.withNameAndCode("Category B", "B"));
    Category categoryC = create(CategoryMother.withNameAndCode("Category C", "C"));
    Item itemA = create(ItemMother.newItem(categoryA));
    Item itemB = create(ItemMother.newItem(categoryB));

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
  void findAllByStatusIsEmpty() {
    assertThat(repository.findAllByStatus(null)).isEmpty();
  }

  @Test
  void findAllByStatus() {
    Item itemA = create(ItemMother.newItem(ItemStatus.AVAILABLE));
    Item itemB = create(ItemMother.newItem(ItemStatus.AVAILABLE));
    create(ItemMother.newItem(ItemStatus.DAMAGED));

    assertThat(repository.findAllByStatus(ItemStatus.AVAILABLE))
        .containsExactlyInAnyOrder(itemA, itemB);
    assertThat(repository.findAllByStatus(ItemStatus.UNAVAILABLE)).isEmpty();
  }
}
