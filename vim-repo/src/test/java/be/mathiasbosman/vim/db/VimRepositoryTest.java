package be.mathiasbosman.vim.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.mathiasbosman.vim.entity.Identifiable;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class VimRepositoryTest extends AbstractRepositoryTest {

  @Mock
  VimRepository<MockObject> repository;

  @Test
  void getById() {
    UUID workingId = UUID.randomUUID();
    UUID failingId = UUID.randomUUID();
    when(repository.findById(workingId)).thenReturn(Optional.of(new MockObject()));
    when(repository.findById(failingId)).thenReturn(Optional.empty());
    assertThat(VimRepository.getById(repository, workingId)).isNotNull();
    assertThrows(IllegalArgumentException.class,
        () -> VimRepository.getById(repository, failingId));
  }

  @Entity
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  static class MockObject implements Identifiable<UUID> {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private UUID id;
  }
}