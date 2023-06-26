package be.mathiasbosman.vim.repository;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.vim.domain.Category;
import be.mathiasbosman.vim.domain.CategoryMother;
import be.mathiasbosman.vim.security.SecurityContext.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(roles = {Role.USER})
class CategoryRestRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  CategoryRestRepository repository;

  @Test
  void getByNameIsEmpty() {
    assertThat(repository.getByName("foo")).isEmpty();
  }

  @Test
  void getByName() {
    Category persistedCategory = create(CategoryMother.randomItem());

    assertThat(repository.getByName(persistedCategory.getName()))
        .isNotEmpty()
        .hasValueSatisfying(cat -> assertThat(cat.getId()).isEqualTo(persistedCategory.getId()));
  }

  @Test
  void getByCode() {
    Category categoryA = create(CategoryMother.randomItem());

    assertThat(repository.getByCode(categoryA.getCode()))
        .hasValue(categoryA);
  }

  @Test
  void getByCodeIsEmpty() {
    assertThat(repository.getByCode("foo")).isEmpty();
  }

  @Test
  void findByCodeContainingOrNameContainingIgnoreCase() {
    Category categoryA = create(CategoryMother.itemWithNameAndCode("Category A", "A"));
    Category categoryB = create(CategoryMother.itemWithNameAndCode("Category B", "B"));
    Category categoryC = create(CategoryMother.itemWithNameAndCode("Category C", "CAT"));

    assertThat(repository.findByCodeContainingOrNameContainingIgnoreCase("a", "a"))
        .containsExactlyInAnyOrder(categoryA, categoryB, categoryC);
    assertThat(repository.findByCodeContainingOrNameContainingIgnoreCase("A", "A"))
        .containsExactlyInAnyOrder(categoryA, categoryB, categoryC);
    assertThat(repository.findByCodeContainingOrNameContainingIgnoreCase("B", "B"))
        .containsExactly(categoryB);
  }

  @Test
  void findByCodeContainingOrNameContainingIgnoreCaseIsEmpty() {
    assertThat(repository.findByCodeContainingOrNameContainingIgnoreCase("D", "d")).isEmpty();
  }

  @Test
  void findByParentCategory() {
    Category parent = create(CategoryMother.randomItem());
    Category subCategory1 = create(CategoryMother.randomItem().toBuilder()
        .parentCategory(parent).build());
    Category subCategory2 = create(CategoryMother.randomItem().toBuilder()
        .parentCategory(parent).build());

    assertThat(repository.findByParentCategory(parent))
        .containsExactlyInAnyOrder(subCategory1, subCategory2);
  }

  @Test
  void findByParentCategoryIsEmpty() {
    Category parent = create(CategoryMother.randomItem());

    assertThat(repository.findByParentCategory(parent)).isEmpty();
  }
}
