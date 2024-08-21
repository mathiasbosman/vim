package be.mathiasbosman.vim.service;

import be.mathiasbosman.vim.domain.*;
import be.mathiasbosman.vim.repository.ItemRestRepository;
import be.mathiasbosman.vim.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link TransactionService} interface.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ItemRestRepository itemRepository;

    @Override
    @Transactional
    public TransactionRecord create(TransactionRecord transaction) {
        Item item = itemRepository.getById(transaction.itemId());
        ItemStatus currentStatus = item.getStatus();
        TransactionType transactionType = transaction.type();
        if (!transactionType.isValidForItemStatus(currentStatus)) {
            throw new VimException(
                    Level.WARN, "Invalid pre-item status (%s) for transaction of type %s",
                    currentStatus, transactionType);
        }

        // adjust the item status
        item.setStatus(transactionType.getPostItemStatus());
        // create the transaction
        Transaction newTransaction = new Transaction(null, item, transactionType);
        return TransactionRecord.fromEntity(transactionRepository.save(newTransaction));
    }

}
