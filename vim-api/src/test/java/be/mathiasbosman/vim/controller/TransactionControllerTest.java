package be.mathiasbosman.vim.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.mathiasbosman.vim.db.TransactionRepository;
import be.mathiasbosman.vim.domain.CategoryRecord;
import be.mathiasbosman.vim.domain.ItemRecord;
import be.mathiasbosman.vim.domain.TransactionRecord;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import be.mathiasbosman.vim.security.SecurityContext.Authority;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(authorities = {Authority.API_USER, Authority.TRANSACTION_WRITE})
class TransactionControllerTest extends AbstractMvcTest {

  @MockBean
  TransactionRepository repository;

  @Test
  void executeTransaction() throws Exception {
    var categoryDto = new CategoryRecord(UUID.randomUUID(), "foo", "bar");
    var itemDto = new ItemRecord(UUID.randomUUID(), "foo", "bar", categoryDto,
        ItemStatus.AVAILABLE);
    var transactionDto = new TransactionRecord(UUID.randomUUID(), itemDto,
        TransactionType.CHECK_OUT);

    when(repository.save(any(Transaction.class))).thenReturn(
        transactionDto.mapToTransactionEntity());

    mvc.perform(postObject("/rest/transaction", transactionDto))
        .andExpect(status().isOk());
  }
}
