package be.mathiasbosman.vim.domain;

import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import java.util.UUID;

/**
 * Record of the {@link Transaction} entity.
 */
public record TransactionDto(UUID id, ItemDto itemDto, TransactionType type) {

  public static TransactionDto fromEntity(Transaction transaction) {
    return new TransactionDto(transaction.getId(), ItemDto.fromEntity(transaction.getItem()),
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
