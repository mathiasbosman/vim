package be.mathiasbosman.vim.repository;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.vim.domain.Category;
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
    Category persistedCategory = create(Category.builder().name("foo").code("bar").build());

    assertThat(repository.getByName("foo"))
        .isNotEmpty()
        .hasValueSatisfying(cat -> assertThat(cat.getId()).isEqualTo(persistedCategory.getId()));
  }

  @Test
  void getByCode() {
    Category categoryA = create(Category.builder().name("foo").code("bar").build());

    assertThat(repository.getByCode(categoryA.getCode()))
        .hasValue(categoryA);
  }

  @Test
  void getByCodeIsEmpty() {
    assertThat(repository.getByCode("foo")).isEmpty();
  }

  @Test
  void findByCodeContainingOrNameContainingIgnoreCase() {
    Category categoryA = create(Category.builder().name("Category A").code("A").build());
    Category categoryB = create(Category.builder().name("Category B").code("B").build());
    Category categoryC = create(Category.builder().name("Category C").code("CAT").build());

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
    Category parent = create(Category.builder().name("Parent cat.").code("P").build());
    Category subCategory1 = create(
        Category.builder().name("Sub cat. 1").code("S1").parentCategory(parent).build());
    Category subCategory2 = create(
        Category.builder().name("Sub cat. 2").code("S2").parentCategory(parent).build());

    assertThat(repository.findByParentCategory(parent))
        .containsExactlyInAnyOrder(subCategory1, subCategory2);
  }

  @Test
  void findByParentCategoryIsEmpty() {
    Category parent = create(Category.builder().name("Parent cat.").code("P").build());

    assertThat(repository.findByParentCategory(parent)).isEmpty();
  }
}
