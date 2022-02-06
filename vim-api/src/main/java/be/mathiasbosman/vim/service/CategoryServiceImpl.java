package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.db.CategoryRepository;
import be.mathiasbosman.vim.entity.Category;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public Category getByCode(String code) {
    return categoryRepository.getByCode(code);
  }

  public List<Category> getSubCategories(Category category) {
    return categoryRepository.findByParentCategory(category);
  }
}
