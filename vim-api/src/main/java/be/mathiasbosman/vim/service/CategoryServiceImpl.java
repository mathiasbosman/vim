package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.db.CategoryRepository;
import be.mathiasbosman.vim.entity.Category;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link CategoryService} interface.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  @Transactional(readOnly = true)
  public Category getByCode(String code) {
    return categoryRepository.getByCode(code);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Category> getSubCategories(Category category) {
    return categoryRepository.findByParentCategory(category);
  }
}
