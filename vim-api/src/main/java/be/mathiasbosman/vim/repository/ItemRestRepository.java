package be.mathiasbosman.vim.repository;

import be.mathiasbosman.vim.domain.Category;
import be.mathiasbosman.vim.domain.Item;
import be.mathiasbosman.vim.domain.ItemStatus;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.Nullable;

@RepositoryRestResource(collectionResourceRel = "items", path = "items")
public interface ItemRestRepository extends PagingAndSortingRepository<Item, UUID> {

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

  Item getById(UUID id);

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
