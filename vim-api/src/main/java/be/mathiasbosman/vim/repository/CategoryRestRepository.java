package be.mathiasbosman.vim.repository;

import be.mathiasbosman.vim.domain.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRestRepository extends PagingAndSortingRepository<Category, UUID> {

  /**
   * Finds a category by name.
   *
   * @param name The name to search for.
   * @return the category found; null if none found.
   */
  Optional<Category> getByName(@Param("name") String name);

  /**
   * Finds a category by code.
   *
   * @param code The code to search for.
   * @return the category found; null if none found.
   */
  Optional<Category> getByCode(@Param("code") String code);

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
