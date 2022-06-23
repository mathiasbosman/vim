package be.mathiasbosman.vim.repository;

import be.mathiasbosman.vim.domain.Item;
import be.mathiasbosman.vim.domain.Transaction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

  /**
   * Find all transactions for a given item.
   *
   * @param item the item to find transactions for
   * @return a list of transactions for the given item
   */
  List<Transaction> findAllByItem(Item item);
}
