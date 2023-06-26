package be.mathiasbosman.vim.domain;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class CategoryMother {

  public static Category itemWithNameAndCode(String name, String code) {
    return Category.builder()
        .name(name)
        .code(code)
        .build();
  }

  public static Category randomItem() {
    return Category.builder()
        .code(RandomStringUtils.randomAlphabetic(3))
        .name(RandomStringUtils.randomAlphabetic(10))
        .build();
  }
}
