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
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Item database entity.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends AbstractAuditedEntity implements Identifiable<UUID> {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "uuid2")
  private UUID id;

  @Column(nullable = false)
  private String name;

  private String brand;

  @ManyToOne
  private Category category;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private ItemStatus status = ItemStatus.AVAILABLE;
}
