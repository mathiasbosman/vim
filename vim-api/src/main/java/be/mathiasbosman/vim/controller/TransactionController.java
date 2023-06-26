package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.TransactionRecord;
import be.mathiasbosman.vim.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller mostly used with the {@link TransactionService}.
 */
@RestController
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping("/rest/transactions")
  public ResponseEntity<TransactionRecord> executeTransaction(
      @RequestBody TransactionRecord transactionRecord) {
    TransactionRecord result = TransactionRecord.fromEntity(
        transactionService.executeTransaction(transactionRecord.mapToTransactionEntity()));
    return ResponseEntity.ok(result);
  }
}
