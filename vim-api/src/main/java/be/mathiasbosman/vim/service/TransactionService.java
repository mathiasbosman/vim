package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.domain.*;

/**
 * Interface holding mostly {@link Transaction} related methods.
 */
public interface TransactionService {

  TransactionRecord create(TransactionRecord transaction);

}
