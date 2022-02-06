package be.mathiasbosman.vim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import be.mathiasbosman.vim.db.ItemRepository;
import be.mathiasbosman.vim.db.TransactionRepository;
import be.mathiasbosman.vim.dto.ItemDto;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TransactionServiceTest extends AbstractServiceTest {

  @InjectMocks
  TransactionService transactionService;

  @Mock
  TransactionRepository transactionRepository;
  @Mock
  ItemRepository itemRepository;

  @BeforeEach
  void setUp() {
    lenient().when(transactionRepository.save(any(Transaction.class)))
        .thenAnswer(i -> i.getArgument(0));
    lenient().when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));
  }

  @Test
  void createForNoneExistingItem() {
    UUID noneExistingItemId = UUID.randomUUID();
    when(itemRepository.findById(noneExistingItemId)).thenReturn(Optional.empty());
    ItemDto noneExistingItem = new ItemDto(noneExistingItemId, "foo", "bar", null,
        ItemStatus.AVAILABLE);
    assertThrows(IllegalArgumentException.class,
        () -> transactionService.create(noneExistingItem, TransactionType.CHECK_IN));
  }

  private ItemDto mockItemDtoInRepositoryForStatus(ItemStatus status) {
    ItemDto itemDto = new ItemDto(UUID.randomUUID(), "foo", "bar", null, status);
    when(itemRepository.findById(any(UUID.class))).thenReturn(
        Optional.of(itemDto.mapToItemEntity()));
    return itemDto;
  }

  private void assertTransactions(TransactionType transactionType,
      ItemStatus expectedPostItemStatus,
      Set<ItemStatus> allowedStatuses, Function<ItemDto, Transaction> method) {

    allowedStatuses.forEach(status -> {
      // create item and mock repository
      ItemDto itemDto = mockItemDtoInRepositoryForStatus(status);
      Transaction transaction = method.apply(itemDto);
      assertThat(transaction).isNotNull();
      assertThat(transaction.getType()).isEqualTo(transactionType);
      assertThat(transaction.getItem().getId()).isEqualTo(itemDto.id());
      assertThat(transaction.getItem().getStatus()).isEqualTo(expectedPostItemStatus);
    });

    // all other statuses should throw exception
    Arrays.stream(ItemStatus.values()).filter(status -> !allowedStatuses.contains(status))
        .forEach(illegalStatus -> {
          ItemDto itemDto = mockItemDtoInRepositoryForStatus(illegalStatus);
          assertThrows(IllegalStateException.class,
              () -> method.apply(itemDto));
        });
  }


  @Test
  void checkIn() {
    assertTransactions(
        TransactionType.CHECK_IN,
        ItemStatus.AVAILABLE,
        Set.of(ItemStatus.CHECKED_OUT, ItemStatus.UNAVAILABLE),
        itemDto -> transactionService.checkIn(itemDto));
  }

  @Test
  void checkOut() {
    assertTransactions(
        TransactionType.CHECK_OUT,
        ItemStatus.CHECKED_OUT,
        Set.of(ItemStatus.AVAILABLE, ItemStatus.RESERVED),
        itemDto -> transactionService.checkOut(itemDto));
  }

  @Test
  void markDamaged() {
    assertTransactions(
        TransactionType.MARK_DAMAGED,
        ItemStatus.DAMAGED,
        Set.of(ItemStatus.AVAILABLE, ItemStatus.UNAVAILABLE, ItemStatus.CHECKED_OUT, ItemStatus.RESERVED),
        itemDto -> transactionService.markDamaged(itemDto));
  }

  @Test
  void markRepaired() {
    assertTransactions(
        TransactionType.MARK_REPAIRED,
        ItemStatus.AVAILABLE,
        Collections.singleton(ItemStatus.DAMAGED),
        itemDto -> transactionService.markRepaired(itemDto));
  }

  @Test
  void remove() {
    assertTransactions(
        TransactionType.REMOVE,
        ItemStatus.UNAVAILABLE,
        Set.of(ItemStatus.AVAILABLE, ItemStatus.DAMAGED),
        itemDto -> transactionService.remove(itemDto));
  }

}