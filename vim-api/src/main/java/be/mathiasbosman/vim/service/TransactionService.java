package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.domain.Transaction;
import be.mathiasbosman.vim.domain.TransactionRecord;

/**
 * Interface holding mostly {@link Transaction} related methods.
 */
public interface TransactionService {

  TransactionRecord create(TransactionRecord transaction);

}
