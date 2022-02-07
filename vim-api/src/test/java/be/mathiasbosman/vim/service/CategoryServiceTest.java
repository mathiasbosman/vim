package be.mathiasbosman.vim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import be.mathiasbosman.vim.db.CategoryRepository;
import be.mathiasbosman.vim.entity.Category;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CategoryServiceTest extends AbstractServiceTest {

  @InjectMocks
  CategoryServiceImpl categoryService;

  @Mock
  CategoryRepository categoryRepository;

  @Test
  void getByCode() {
    Category category = Category.builder().code("foo").name("bar").build();
    when(categoryRepository.getByCode("foo")).thenReturn(category);
    assertThat(categoryService.getByCode("foo")).isEqualTo(category);
  }

  @Test
  void getSubCategories() {
    Category parentCategory = Category.builder().code("foo").name("bar").build();
    Category subCategoryA = Category.builder().code("foo.a").name("bar.a")
        .parentCategory(parentCategory).build();
    Category subCategoryB = Category.builder().code("foo.b").name("bar.b")
        .parentCategory(parentCategory).build();
    when(categoryRepository.findByParentCategory(parentCategory)).thenReturn(
        List.of(subCategoryA, subCategoryB));
    assertThat(categoryService.getSubCategories(parentCategory))
        .containsExactlyInAnyOrder(subCategoryA, subCategoryB);
  }
}
