package be.mathiasbosman.vim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The Transaction database entity.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AbstractAuditedEntity implements Identifiable<UUID> {

  @Id
  @GeneratedValue
  private UUID id;

  @ManyToOne
  private Item item;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionType type;
}
