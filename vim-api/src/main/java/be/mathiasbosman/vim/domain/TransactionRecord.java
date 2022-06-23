package be.mathiasbosman.vim.domain;

import java.util.UUID;

/**
 * Record of the {@link Transaction} entity.
 */
public record TransactionRecord(UUID id, TransactionType type, ItemRecord itemDto) {

  public static TransactionRecord fromEntity(Transaction transaction) {
    return new TransactionRecord(
        transaction.getId(),
        transaction.getType(),
        ItemRecord.fromEntity(transaction.getItem()));
  }

  /**
   * Maps the record to the {@link Transaction} entity.
   *
   * @return an entity of {@link Transaction}
   */
  public Transaction mapToTransactionEntity() {
    return Transaction.builder()
        .id(id)
        .type(type)
        .item(itemDto.mapToItemEntity())
        .build();
  }
}
