package be.mathiasbosman.vim.domain;

import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import java.util.UUID;

/**
 * Record of the {@link Transaction} entity.
 */
public record TransactionRecord(UUID id, ItemRecord itemDto, TransactionType type) {

  public static TransactionRecord fromEntity(Transaction transaction) {
    return new TransactionRecord(transaction.getId(), ItemRecord.fromEntity(transaction.getItem()),
        transaction.getType());
  }

  /**
   * Maps the record to the {@link Transaction} entity.
   *
   * @return an entity of {@link Transaction}
   */
  public Transaction mapToTransactionEntity() {
    return Transaction.builder()
        .id(id)
        .item(itemDto.mapToItemEntity())
        .type(type)
        .build();
  }
}
