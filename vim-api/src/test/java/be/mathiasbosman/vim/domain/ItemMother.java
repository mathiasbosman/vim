package be.mathiasbosman.vim.domain;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class ItemMother {

  public static Item randomItem() {
    return Item.builder()
        .name(RandomStringUtils.random(255))
        .build();
  }

}
