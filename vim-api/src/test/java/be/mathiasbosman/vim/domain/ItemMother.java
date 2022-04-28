package be.mathiasbosman.vim.domain;

import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemMother {

  public static Item availableItem() {
    return Item.builder().name("foo").brand("bar").status(ItemStatus.AVAILABLE).build();
  }
}
