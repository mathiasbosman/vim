package be.mathiasbosman.vim.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import be.mathiasbosman.vim.db.ItemRepository;
import be.mathiasbosman.vim.entity.Category;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class ItemControllerTest extends AbstractControllerTest {

  @MockBean
  private ItemRepository itemRepository;

  private Item mockItem(UUID id, ItemStatus status) {
    Category mockCategory = Category.builder().name("test").build();
    return Item.builder().id(id).name("foo_" + id).category(mockCategory).status(status).build();
  }

  @Test
  void getItems() throws Exception {
    Item mockItem0 = mockItem(UUID.randomUUID(), ItemStatus.AVAILABLE);
    Item mockItem1 = mockItem(UUID.randomUUID(), ItemStatus.AVAILABLE);
    when(itemRepository.findAllByStatus(ItemStatus.AVAILABLE)).thenReturn(
        List.of(mockItem0, mockItem1));
    mvc.perform(
            get("/rest/items").param("status", ItemStatus.AVAILABLE.toString()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(mockItem0.getId().toString()))
        .andExpect(jsonPath("$[1].id").value(mockItem1.getId().toString()))
        .andReturn();

    mvc.perform(get("/rest/items")).andExpect(status().isBadRequest());
    mvc.perform(get("/rest/items").param("status", "invalid"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findItem() throws Exception {
    Item mockItem = mockItem(UUID.randomUUID(), ItemStatus.UNAVAILABLE);
    when(itemRepository.findById(mockItem.getId())).thenReturn(Optional.of(mockItem));
    mvc.perform(get("/rest/item/find").param("uuid", mockItem.getId().toString()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(mockItem.getId().toString()));
    UUID invalidUuid = UUID.randomUUID();
    when(itemRepository.findById(invalidUuid)).thenReturn(Optional.empty());
    mvc.perform(get("/rest/item/find").param("uuid", invalidUuid.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());
  }

}
