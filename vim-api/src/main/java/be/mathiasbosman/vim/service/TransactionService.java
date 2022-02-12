package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;

/**
 * Interface holding mostly {@link Transaction} related methods.
 */
public interface TransactionService {

  Transaction create(Item item, TransactionType transactionType);

  Transaction executeTransaction(Transaction transaction);
}
