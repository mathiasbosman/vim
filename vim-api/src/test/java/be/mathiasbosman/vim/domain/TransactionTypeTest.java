package be.mathiasbosman.vim.domain;

import static be.mathiasbosman.vim.domain.TransactionType.CHECK_IN;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TransactionTypeTest {

  @Test
  void isValidForItemStatus() {
    assertThat(CHECK_IN.isValidForItemStatus(ItemStatus.AVAILABLE)).isFalse();
    assertThat(CHECK_IN.isValidForItemStatus(ItemStatus.UNAVAILABLE)).isTrue();
  }
}
