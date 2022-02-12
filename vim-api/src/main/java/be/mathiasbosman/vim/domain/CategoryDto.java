package be.mathiasbosman.vim.domain;

import be.mathiasbosman.vim.entity.Category;
import java.util.UUID;

/**
 * Record for the {@link Category} entity.
 */
public record CategoryDto(UUID id, String name, String code) {

  public static CategoryDto fromEntity(Category category) {
    return new CategoryDto(category.getId(), category.getName(), category.getCode());
  }

  /**
   * Maps the record to the {@link Category} entity.
   *
   * @return an entity of {@link Category}
   */
  public Category mapToCategoryEntity() {
    return Category.builder()
        .id(id)
        .name(name)
        .code(code)
        .build();
  }
}
