package be.mathiasbosman.vim.db;

import be.mathiasbosman.vim.entity.Category;
import be.mathiasbosman.vim.entity.Item;
import be.mathiasbosman.vim.entity.ItemStatus;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

/**
 * Repository for the {@link Item} entity.
 *
 * @author Mathias Bosman
 * @since 0.0.1
 */
public interface ItemRepository extends VimRepository<Item> {

  /**
   * Returns all {@link Item}s that have ba name like the given parameter.
   *
   * @param name The name to search for
   * @return list of items
   */
  List<Item> findAllByNameContainingIgnoreCase(String name);

  /**
   * Returns all {@link Item}s in a given {@link Category}.
   *
   * @param category The category to search for
   * @return list of items
   */
  List<Item> findAllByCategory(Category category);

  /**
   * Returns all {@link Item}s that have a given {@link ItemStatus}.
   *
   * @param status The status to search for
   * @return list of items
   */
  List<Item> findAllByStatus(ItemStatus status);

  /**
   * Returns all {@link Item}s that have a given {@link ItemStatus} or given {@link Category} or
   * given {@link ItemStatus}.
   *
   * @param name       Name to search for
   * @param categories Categories to search for
   * @param statuses   Statuses to search for
   * @return list of items
   */
  @Query("select i from Item i where upper(i.name) like upper(concat('%', ?1, '%'))"
      + " or i.category in ?2 or i.status in ?3")
  List<Item> searchItems(@Nullable String name, @Nullable Collection<Category> categories,
      @Nullable Collection<ItemStatus> statuses);

}
