package be.mathiasbosman.vim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import be.mathiasbosman.vim.domain.*;
import be.mathiasbosman.vim.repository.ItemRestRepository;
import be.mathiasbosman.vim.repository.TransactionRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TransactionServiceTest extends AbstractServiceTest {

  @InjectMocks
  TransactionServiceImpl transactionService;

  @Mock
  TransactionRepository transactionRepository;
  @Mock
  ItemRestRepository itemRepository;

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
      Item item = ItemMother.random().toBuilder().id(UUID.randomUUID()).status(status).build();
      when(itemRepository.getById(item.getId())).thenReturn(item);
      TransactionRecord transaction = transactionService.create(new TransactionRecord(transactionType, item.getId()));

      assertThat(transaction).isNotNull();
      assertThat(transaction.type()).isEqualTo(transactionType);
      assertThat(transaction.itemId()).isEqualTo(item.getId());
      assertThat(item.getStatus()).isEqualTo(expectedPostItemStatus);
    });

    // all other statuses should throw exception
    Arrays.stream(ItemStatus.values()).filter(status -> !allowedStatuses.contains(status))
        .forEach(illegalStatus -> {
          Item item = ItemMother.random(illegalStatus);
          when(itemRepository.getById(item.getId())).thenReturn(item);
          assertThatThrownBy(() -> transactionService.create(new TransactionRecord(transactionType, item.getId())))
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