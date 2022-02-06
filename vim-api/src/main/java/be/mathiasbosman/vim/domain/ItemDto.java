package be.mathiasbosman.vim.domain;

import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import java.util.UUID;

/**
 * Record for the {@link Item} entity.
 */
public record ItemDto(UUID id, String name, String brand, CategoryDto categoryDto,
                      ItemStatus status) {

  public static ItemDto fromEntity(Item item) {
    return new ItemDto(item.getId(), item.getName(), item.getBrand(),
        CategoryDto.fromEntity(item.getCategory()), item.getStatus());
  }

  /**
   * Maps the record to an {@link Item} entity.
   *
   * @return the {@link Item} entity
   */
  public Item mapToItemEntity() {
    return Item.builder().id(id)
        .name(name)
        .brand(brand)
        .category(categoryDto != null ? categoryDto.mapToCategoryEntity() : null)
        .status(status)
        .build();
  }
}
