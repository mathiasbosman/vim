package be.mathiasbosman.vim.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "uuid2")
  private UUID id;

  @ManyToOne
  private Item item;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionType type;
}
