package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.db.TransactionRepository;
import be.mathiasbosman.vim.domain.VimException;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link TransactionService} interface.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;

  @Override
  public Transaction create(Item item, TransactionType transactionType) {
    ItemStatus currentStatus = item.getStatus();
    if (!transactionType.isValidForItemStatus(currentStatus)) {
      throw new VimException(
          Level.WARN, "Invalid pre-item status (%s) for transaction of type %s",
          currentStatus, transactionType);
    }

    // adjust the item status
    item.setStatus(transactionType.getPostItemStatus());
    // create the transaction
    Transaction transaction = Transaction.builder()
        .item(item)
        .type(transactionType)
        .build();
    return transactionRepository.save(transaction);
  }

  @Override
  public Transaction executeTransaction(Transaction transaction) {
    return create(transaction.getItem(), transaction.getType());
  }
}
