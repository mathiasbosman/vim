package be.mathiasbosman.vim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import be.mathiasbosman.vim.domain.Item;
import be.mathiasbosman.vim.domain.ItemMother;
import be.mathiasbosman.vim.domain.ItemStatus;
import be.mathiasbosman.vim.domain.Transaction;
import be.mathiasbosman.vim.domain.TransactionType;
import be.mathiasbosman.vim.domain.VimException;
import be.mathiasbosman.vim.repository.TransactionRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TransactionServiceTest extends AbstractServiceTest {

  @InjectMocks
  TransactionServiceImpl transactionService;

  @Mock
  TransactionRepository transactionRepository;

  @BeforeEach
  void setUp() {
    lenient().when(transactionRepository.save(any(Transaction.class)))
        .thenAnswer(returnsFirstArg());
  }

  private void assertTransactions(TransactionType transactionType,
      ItemStatus expectedPostItemStatus,
      Set<ItemStatus> allowedStatuses) {

    allowedStatuses.forEach(status -> {
      // create item and mock repository
      Item item = ItemMother.randomItem().toBuilder().status(status).build();
      Transaction transaction = transactionService.create(item, transactionType);

      assertThat(transaction).isNotNull();
      assertThat(transaction.getType()).isEqualTo(transactionType);
      assertThat(transaction.getItem().getId()).isEqualTo(item.getId());
      assertThat(transaction.getItem().getStatus()).isEqualTo(expectedPostItemStatus);
    });

    // all other statuses should throw exception
    Arrays.stream(ItemStatus.values()).filter(status -> !allowedStatuses.contains(status))
        .forEach(illegalStatus -> {
          Item item = ItemMother.randomItem().toBuilder().status(illegalStatus).build();
          assertThatThrownBy(() -> transactionService.create(item, transactionType))
              .isInstanceOf(VimException.class);
        });
  }


  @Test
  void checkIn() {
    assertTransactions(
        TransactionType.CHECK_IN,
        ItemStatus.AVAILABLE,
        Set.of(ItemStatus.CHECKED_OUT, ItemStatus.UNAVAILABLE));
  }

  @Test
  void checkOut() {
    assertTransactions(
        TransactionType.CHECK_OUT,
        ItemStatus.CHECKED_OUT,
        Set.of(ItemStatus.AVAILABLE, ItemStatus.RESERVED));
  }

  @Test
  void markDamaged() {
    assertTransactions(
        TransactionType.MARK_DAMAGED,
        ItemStatus.DAMAGED,
        Set.of(ItemStatus.AVAILABLE, ItemStatus.UNAVAILABLE, ItemStatus.CHECKED_OUT,
            ItemStatus.RESERVED));
  }

  @Test
  void markRepaired() {
    assertTransactions(
        TransactionType.MARK_REPAIRED,
        ItemStatus.AVAILABLE,
        Collections.singleton(ItemStatus.DAMAGED));
  }

  @Test
  void remove() {
    assertTransactions(
        TransactionType.REMOVE,
        ItemStatus.UNAVAILABLE,
        Set.of(ItemStatus.AVAILABLE, ItemStatus.DAMAGED));
  }

}