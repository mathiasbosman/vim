package be.mathiasbosman.vim.controller;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
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
    var transactionDto = new TransactionRecord(TransactionType.CHECK_OUT, UUID.randomUUID());

    when(repository.save(any(Transaction.class))).thenAnswer(returnsFirstArg());

    mvc.perform(postObject("/transactions", transactionDto))
        .andExpect(status().isOk());
  }
}
