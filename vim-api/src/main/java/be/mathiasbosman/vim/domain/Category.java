package be.mathiasbosman.vim.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Category database entity.
 */
@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Category extends AbstractDeletableEntity implements Identifiable<UUID> {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(unique = true, nullable = false)
  private String name;
  @Column(unique = true, nullable = false)
  private String code;

  @ManyToOne
  private Category parentCategory;
}
