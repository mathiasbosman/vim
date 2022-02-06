package be.mathiasbosman.vim.entity;

import static be.mathiasbosman.vim.entity.TransactionType.CHECK_IN;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TransactionTypeTest {

  @Test
  void isValidForItemStatus() {
    assertThat(CHECK_IN.isValidForItemStatus(ItemStatus.AVAILABLE)).isFalse();
    assertThat(CHECK_IN.isValidForItemStatus(ItemStatus.UNAVAILABLE)).isTrue();
  }
}
