package be.mathiasbosman.vim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractDeletableEntity extends AbstractAuditedEntity {

  @Column
  private boolean deleted = Boolean.FALSE;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime deletedAt;
}
