package be.mathiasbosman.vim.db;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TransactionRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private TransactionRepository repository;

  @Test
  void findAllByItem() {
    assertThat(repository.findAllByItem(null)).isEmpty();
    Item itemA = create(Item.builder().name("The item A").build());
    assertThat(repository.findAllByItem(itemA)).isEmpty();
    Transaction transaction1 = create(Transaction.builder()
        .type(TransactionType.CHECK_IN)
        .item(itemA).build());
    assertThat(repository.findAllByItem(itemA)).containsExactly(transaction1);
    Transaction transaction2 = create(Transaction.builder()
        .type(TransactionType.CHECK_OUT)
        .item(itemA).build());
    assertThat(repository.findAllByItem(itemA)).containsExactly(transaction1, transaction2);
  }
}
