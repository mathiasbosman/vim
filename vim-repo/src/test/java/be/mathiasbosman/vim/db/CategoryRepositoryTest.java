package be.mathiasbosman.vim.db;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.vim.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private CategoryRepository repository;

  @Test
  void getByCode() {
    Category categoryA = create(Category.builder().name("Category A").code("A").build());
    assertThat(repository.getByCode("A")).isEqualTo(categoryA);
    assertThat(repository.getByCode("B")).isNull();
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
    assertThat(repository.findByCodeContainingOrNameContainingIgnoreCase("D", "d")).isEmpty();
  }

  @Test
  void findByParentCategory() {
    Category parent = create(Category.builder().name("Parent cat.").code("P").build());
    Category subCategory1 = create(Category.builder().name("Sub cat. 1").code("S1").parentCategory(parent).build());
    Category subCategory2 = create(Category.builder().name("Sub cat. 2").code("S2").parentCategory(parent).build());
    assertThat(repository.findByParentCategory(parent))
        .containsExactlyInAnyOrder(subCategory1, subCategory2);
  }
}
