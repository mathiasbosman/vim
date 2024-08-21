package be.mathiasbosman.vim.domain;

import java.util.UUID;

/**
 * Record of the {@link Transaction} entity.
 */
public record TransactionRecord(UUID id, TransactionType type, UUID itemId) {

  public TransactionRecord(TransactionType type, UUID itemId) {
    this(null, type, itemId);
  }

  public static TransactionRecord fromEntity(Transaction transaction) {
    return new TransactionRecord(
        transaction.getId(),
        transaction.getType(),
        transaction.getItem().getId());
  }
}
