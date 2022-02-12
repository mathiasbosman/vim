package be.mathiasbosman.vim.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.mathiasbosman.vim.config.SecurityConfig;
import be.mathiasbosman.vim.db.TransactionRepository;
import be.mathiasbosman.vim.domain.CategoryDto;
import be.mathiasbosman.vim.domain.ItemDto;
import be.mathiasbosman.vim.domain.TransactionDto;
import be.mathiasbosman.vim.entity.ItemStatus;
import be.mathiasbosman.vim.entity.Transaction;
import be.mathiasbosman.vim.entity.TransactionType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@WithMockUser(roles = {SecurityConfig.API_USER_ROLE})
public class TransactionControllerTest extends AbstractControllerTest {

  @MockBean
  TransactionRepository repository;

  @Test
  void executeTransaction() throws Exception {
    CategoryDto categoryDto = new CategoryDto(UUID.randomUUID(), "foo", "bar");
    ItemDto itemDto = new ItemDto(UUID.randomUUID(), "foo", "bar", categoryDto,
        ItemStatus.AVAILABLE);
    TransactionDto transactionDto = new TransactionDto(UUID.randomUUID(), itemDto,
        TransactionType.CHECK_OUT);
    when(repository.save(any(Transaction.class))).thenReturn(
        transactionDto.mapToTransactionEntity());
    mvc.perform(postObject("/rest/transaction", transactionDto, true))
        .andExpect(status().isOk());
    mvc.perform(postObject("/rest/transaction", null, true))
        .andExpect(status().isBadRequest());
  }
}
