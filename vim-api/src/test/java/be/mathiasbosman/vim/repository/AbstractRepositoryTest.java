package be.mathiasbosman.vim.repository;

import be.mathiasbosman.vim.AbstractSpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public abstract class AbstractRepositoryTest extends AbstractSpringBootTest {

  @Autowired
  protected TestEntityManager entityManager;

  protected <E> E create(E entity) {
    return entityManager.persist(entity);
  }

}
