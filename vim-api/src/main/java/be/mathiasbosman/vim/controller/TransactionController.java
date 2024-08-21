package be.mathiasbosman.vim.controller;

import be.mathiasbosman.vim.domain.TransactionRecord;
import be.mathiasbosman.vim.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller mostly used with the {@link TransactionService}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(TransactionController.REQUEST_MAPPING)
public class TransactionController {

    public static final String REQUEST_MAPPING = "/transactions";

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionRecord> executeTransaction(@RequestBody TransactionRecord transactionRecord) {
        log.trace("POST /transactions with {}", transactionRecord);
        TransactionRecord result = transactionService.create(transactionRecord);
        return ResponseEntity.ok(result);
    }
}
