package be.mathiasbosman.vim.repository;

import be.mathiasbosman.vim.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, UUID>,
    CrudRepository<User, UUID> {

  Optional<User> getByUsername(String username);

}
