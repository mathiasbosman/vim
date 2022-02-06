package be.mathiasbosman.vim.db;

import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.Transaction;
import java.util.List;

/**
 * Repository for the {@link Transaction} entity.
 *
 * @author Mathias Bosman
 * @since 0.0.1
 */
public interface TransactionRepository extends VimRepository<Transaction> {

  /**
   * Find all transactions for a given item.
   *
   * @param item the item to find transactions for
   * @return a list of transactions for the given item
   */
  List<Transaction> findAllByItem(Item item);
}
