package be.mathiasbosman.vim.domain;

import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import java.util.UUID;

/**
 * Record of the {@link Transaction} entity.
 */
public record TransactionDto(UUID id, ItemDto itemDto, TransactionType type) {

}
