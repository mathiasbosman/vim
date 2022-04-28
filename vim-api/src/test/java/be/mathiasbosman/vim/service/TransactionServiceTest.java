package be.mathiasbosman.vim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import be.mathiasbosman.vim.db.TransactionRepository;
import be.mathiasbosman.vim.domain.VimException;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
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
  ItemService itemService;

  @BeforeEach
  void setUp() {
    lenient().when(transactionRepository.save(any(Transaction.class)))
        .thenAnswer(i -> i.getArgument(0));
    lenient().when(itemService.saveItem(any(Item.class))).thenAnswer(i -> i.getArgument(0));
  }

  private Item mockItemDtoInRepositoryForStatus(ItemStatus status) {
    Item item = Item.builder().id(UUID.randomUUID()).name("foo").status(status).build();
    lenient().when(itemService.findById(any(UUID.class))).thenReturn(
        Optional.of(item));
    return item;
  }

  private void assertTransactions(TransactionType transactionType,
      ItemStatus expectedPostItemStatus,
      Set<ItemStatus> allowedStatuses) {

    allowedStatuses.forEach(status -> {
      // create item and mock repository
      Item item = mockItemDtoInRepositoryForStatus(status);
      Transaction transaction = transactionService.create(item, transactionType);
      assertThat(transaction).isNotNull();
      assertThat(transaction.getType()).isEqualTo(transactionType);
      assertThat(transaction.getItem().getId()).isEqualTo(item.getId());
      assertThat(transaction.getItem().getStatus()).isEqualTo(expectedPostItemStatus);
    });

    // all other statuses should throw exception
    Arrays.stream(ItemStatus.values()).filter(status -> !allowedStatuses.contains(status))
        .forEach(illegalStatus -> {
          Item item = mockItemDtoInRepositoryForStatus(illegalStatus);
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