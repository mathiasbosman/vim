package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.db.ItemRepository;
import be.mathiasbosman.vim.db.TransactionRepository;
import be.mathiasbosman.vim.dto.ItemDto;
import be.mathiasbosman.vim.dto.TransactionDto;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

  private final ItemRepository itemRepository;
  private final TransactionRepository transactionRepository;

  Transaction create(ItemDto itemDto, TransactionType transactionType) {
    // check if the item is available
    Optional<Item> item = itemRepository.findById(itemDto.id());
    if (item.isEmpty()) {
      //todo: create validation exception
      throw new IllegalArgumentException("Item not found");
    }

    Item itemEntity = item.get();
    ItemStatus currentStatus = itemEntity.getStatus();
    if (!transactionType.isValidForItemStatus(currentStatus)) {
      // todo: create validation exception
      log.error("Invalid pre-item status ({}) for transaction type {}", currentStatus, transactionType);
      throw new IllegalStateException("Invalid pre-item status");
    }

    // adjust the item status
    itemEntity.setStatus(transactionType.getPostItemStatus());
    itemRepository.save(itemEntity);
    // create the transaction
    Transaction transaction = Transaction.builder()
        .item(itemEntity)
        .type(transactionType)
        .build();
    return transactionRepository.save(transaction);
  }

  public Transaction checkIn(ItemDto itemDto) {
    return create(itemDto, TransactionType.CHECK_IN);
  }

  public Transaction checkOut(ItemDto itemDto) {
    return create(itemDto, TransactionType.CHECK_OUT);
  }

  public Transaction markDamaged(ItemDto itemDto) {
    return create(itemDto, TransactionType.MARK_DAMAGED);
  }

  public Transaction markRepaired(ItemDto itemDto) {
    return create(itemDto, TransactionType.MARK_REPAIRED);
  }

  public Transaction remove(ItemDto itemDto) {
    return create(itemDto, TransactionType.REMOVE);
  }

}
