package be.mathiasbosman.vim.db;

import be.mathiasbosman.vim.entity.Category;
import java.util.List;

/**
 * Repository for the {@link Category} entity.
 *
 * @author Mathias Bosman
 * @since 0.0.1
 */
public interface CategoryRepository extends VimRepository<Category> {

  /**
   * Finds a category by code.
   *
   * @param code The code to search for.
   * @return the category found; null if none found.
   */
  Category getByCode(String code);

  /**
   * Finds all categories containing the given name or code.
   *
   * @param code Code to search for.
   * @param name Name to search for.
   * @return List of categories found.
   */
  List<Category> findByCodeContainingOrNameContainingIgnoreCase(String code, String name);

  /**
   * Returns all categories that have the given category as parent.
   *
   * @param parentCategory The parent category.
   * @return List of categories found.
   */
  List<Category> findByParentCategory(Category parentCategory);
}
