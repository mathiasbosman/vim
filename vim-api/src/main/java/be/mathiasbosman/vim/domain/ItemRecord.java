package be.mathiasbosman.vim.domain;

import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import java.util.UUID;

/**
 * Record for the {@link Item} entity.
 */
public record ItemRecord(UUID id, String name, String brand, CategoryRecord categoryRecord,
                         ItemStatus status) {

  public static ItemRecord fromEntity(Item item) {
    return new ItemRecord(item.getId(), item.getName(), item.getBrand(),
        CategoryRecord.fromEntity(item.getCategory()), item.getStatus());
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
