package be.mathiasbosman.vim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Transactional
@Testcontainers
@AutoConfigureTestEntityManager
public abstract class AbstractSpringBootTest {

  @Container
  @SuppressWarnings("unused")
  public static PostgreSQLContainer<VimPostgresqlContainer> postgreSQLContainer = VimPostgresqlContainer.getInstance();

  @Autowired
  protected TestEntityManager entityManager;
}
