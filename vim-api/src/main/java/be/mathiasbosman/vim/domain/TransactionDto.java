package be.mathiasbosman.vim.dto;

import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import java.util.UUID;

/**
 * Record of the {@link Transaction} entity.
 */
public record TransactionDto(UUID id, ItemDto itemDto, TransactionType type) {

}
