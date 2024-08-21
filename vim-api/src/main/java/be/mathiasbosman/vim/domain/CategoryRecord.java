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
}
