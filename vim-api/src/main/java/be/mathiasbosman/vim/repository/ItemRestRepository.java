package be.mathiasbosman.vim.repository;

import be.mathiasbosman.vim.domain.Category;
import be.mathiasbosman.vim.domain.Item;
import be.mathiasbosman.vim.domain.ItemStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

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

}
