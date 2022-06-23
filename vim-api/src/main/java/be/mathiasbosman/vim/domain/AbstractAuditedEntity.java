package be.mathiasbosman.vim.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Abstract class for entities that have an audit trail.
 */
@Getter
@MappedSuperclass
public abstract class AbstractAuditedEntity implements AuditableEntity {

  @Column(updatable = false)
  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime created;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private LocalDateTime updated;

  @PrePersist
  void prePersist() {
    created = LocalDateTime.now();
    updated = created;
  }

  @PreUpdate
  void preUpdate() {
    updated = LocalDateTime.now();
  }
}
