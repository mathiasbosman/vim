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

  /**
   * Maps the record to an {@link Item} entity.
   *
   * @return an entity of {@link Item}
   */
  public Item mapToItemEntity() {
    return Item.builder().id(id)
        .name(name)
        .brand(brand)
        .category(categoryRecord != null ? categoryRecord.mapToCategoryEntity() : null)
        .status(status)
        .build();
  }
}
