package be.mathiasbosman.vim.repository;

import be.mathiasbosman.vim.security.Role;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {

  Role getByName(String name);
}
