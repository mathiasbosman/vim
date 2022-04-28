package be.mathiasbosman.vim;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
public class VimPostgresqlContainer extends PostgreSQLContainer<VimPostgresqlContainer> {

  private static final String IMAGE_VERSION = "postgres:11.1";
  private static VimPostgresqlContainer container;

  private VimPostgresqlContainer() {
    super(IMAGE_VERSION);
  }

  public static VimPostgresqlContainer getInstance() {
    if (container == null) {
      container = new VimPostgresqlContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
    log.info("Container started on {}", container.getJdbcUrl());
  }

  @Override
  public void stop() {
    log.info("Container stopped");
  }
}
