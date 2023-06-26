package be.mathiasbosman.vim.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.mathiasbosman.vim.AbstractMvcTest;
import be.mathiasbosman.vim.domain.CategoryRecord;
import be.mathiasbosman.vim.domain.ItemRecord;
import be.mathiasbosman.vim.domain.ItemStatus;
import be.mathiasbosman.vim.domain.Transaction;
import be.mathiasbosman.vim.domain.TransactionRecord;
import be.mathiasbosman.vim.domain.TransactionType;
import be.mathiasbosman.vim.repository.TransactionRepository;
import be.mathiasbosman.vim.security.SecurityContext.Role;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(roles = {Role.USER})
class TransactionControllerTest extends AbstractMvcTest {

  @MockBean
  TransactionRepository repository;

  @Test
  void executeTransaction() throws Exception {
    var categoryDto = new CategoryRecord(UUID.randomUUID(), "foo", "bar");
    var itemDto = new ItemRecord(
        UUID.randomUUID(), "foo", "bar", ItemStatus.AVAILABLE, categoryDto);
    var transactionDto = new TransactionRecord(
        UUID.randomUUID(), TransactionType.CHECK_OUT, itemDto);

    when(repository.save(any(Transaction.class))).thenReturn(
        transactionDto.mapToTransactionEntity());

    mvc.perform(postObject("/rest/transactions", transactionDto))
        .andExpect(status().isOk());
  }
}
