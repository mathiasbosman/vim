package be.mathiasbosman.vim.repository;

import be.mathiasbosman.vim.domain.RefreshToken;
import be.mathiasbosman.vim.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID> {

  Optional<RefreshToken> findByUser(User user);

  Optional<RefreshToken> findByToken(UUID token);
}
