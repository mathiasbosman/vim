package be.mathiasbosman.vim.repository;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.vim.domain.Item;
import be.mathiasbosman.vim.domain.Transaction;
import be.mathiasbosman.vim.domain.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TransactionRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private TransactionRepository repository;

  @Test
  void findAllByItemIsEmpty() {
    Item itemA = create(Item.builder().name("The item A").build());

    assertThat(repository.findAllByItem(null)).isEmpty();
    assertThat(repository.findAllByItem(itemA)).isEmpty();
  }

  @Test
  void findAllByItem() {
    Item item = create(Item.builder().name("The item A").build());
    Transaction transaction1 = create(Transaction.builder()
        .type(TransactionType.CHECK_IN)
        .item(item).build());
    assertThat(repository.findAllByItem(item)).containsExactly(transaction1);

    // create second transaction
    Transaction transaction2 = create(Transaction.builder()
        .type(TransactionType.CHECK_OUT)
        .item(item).build());

    assertThat(repository.findAllByItem(item)).containsExactly(transaction1, transaction2);
  }
}
