package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.TransactionDto;
import be.mathiasbosman.vim.service.TransactionService;
import lombok.RequiredArgsConstructor;
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

  @PostMapping("/rest/transaction")
  public TransactionDto executeTransaction(@RequestBody TransactionDto transactionDto) {
    return TransactionDto.fromEntity(
        transactionService.executeTransaction(transactionDto.mapToTransactionEntity()));
  }
}
