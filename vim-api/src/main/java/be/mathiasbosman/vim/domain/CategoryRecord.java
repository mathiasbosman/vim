package be.mathiasbosman.vim.domain;

import java.util.UUID;

/**
 * Record for the {@link Category} entity.
 */
public record CategoryRecord(UUID id, String name, String code) {

  public static CategoryRecord fromEntity(Category category) {
    if (category == null) {
      return null;
    }
    return new CategoryRecord(category.getId(), category.getName(), category.getCode());
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
