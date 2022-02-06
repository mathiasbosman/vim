package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.entity.Category;
import java.util.List;

/**
 * Interface holding mostly {@link Category} related methods.
 */
public interface CategoryService {

  Category getByCode(String code);

  List<Category> getSubCategories(Category category);
}
