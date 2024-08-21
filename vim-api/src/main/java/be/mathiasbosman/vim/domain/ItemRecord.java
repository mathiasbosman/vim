package be.mathiasbosman.vim.domain;

import java.util.UUID;

/**
 * Record for the {@link Item} entity.
 */
public record ItemRecord(UUID id,
                         String name,
                         String brand,
                         ItemStatus status,
                         CategoryRecord categoryRecord) {

  public static ItemRecord fromEntity(Item item) {
    return new ItemRecord(
        item.getId(),
        item.getName(),
        item.getBrand(),
        item.getStatus(),
        CategoryRecord.fromEntity(item.getCategory()));
  }
}
