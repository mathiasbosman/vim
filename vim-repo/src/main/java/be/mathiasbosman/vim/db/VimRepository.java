package be.mathiasbosman.vim.db;

import be.mathiasbosman.vim.entity.Identifiable;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Super interface that can be used to find any entity in a given repository.
 *
 * @param <E> the type of {@link Identifiable} used in the repository
 * @author Mathias Bosman
 * @since 1.0.0
 */
@NoRepositoryBean
public interface VimRepository<E extends Identifiable<UUID>> extends CrudRepository<E, UUID> {

  /**
   * Returns an entity from a given repository based on its identifier.
   *
   * @param repo the {@link VimRepository} to query
   * @param id   {@link UUID} of the entity to search
   * @return the entity found
   */
  static <E extends Identifiable<UUID>> E getById(VimRepository<E> repo, UUID id) {
    return repo.findById(id).orElseThrow(() -> new IllegalArgumentException(
        "No result for id = " + id + " in repository " + repo.getClass()));
  }
}
