package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.domain.Item;
import be.mathiasbosman.vim.domain.Transaction;
import be.mathiasbosman.vim.domain.TransactionType;

/**
 * Interface holding mostly {@link Transaction} related methods.
 */
public interface TransactionService {

  Transaction create(Item item, TransactionType transactionType);

  Transaction executeTransaction(Transaction transaction);
}
