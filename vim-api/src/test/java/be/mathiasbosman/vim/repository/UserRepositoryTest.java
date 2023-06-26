package be.mathiasbosman.vim.repository;

import static org.assertj.core.api.Assertions.assertThat;

import be.mathiasbosman.vim.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private UserRepository repository;

  @Test
  void getByUsernameIsEmpty() {
    assertThat(repository.getByUsername("foo")).isEmpty();
  }

  @Test
  void getByUsername() {
    User user = create(User.builder().username("foo").password("bar").build());

    assertThat(repository.getByUsername("foo")).hasValue(user);
  }

}
