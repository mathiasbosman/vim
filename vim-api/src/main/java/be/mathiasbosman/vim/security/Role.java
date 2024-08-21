package be.mathiasbosman.vim.security;

import be.mathiasbosman.vim.domain.AbstractAuditedEntity;
import be.mathiasbosman.vim.domain.Identifiable;
import be.mathiasbosman.vim.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`role`")
public class Role extends AbstractAuditedEntity implements Identifiable<UUID> {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  private boolean systemRole;

  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "role_permission",
      joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(
          name = "permission_id", referencedColumnName = "id"))
  private List<Permission> permissions;

  @PreRemove
  public void preventRemoveIfSystemRole() {
    if (isSystemRole()) {
      throw new UnsupportedOperationException(
          "Role " + name + " is a system role and cannot be removed.");
    }
  }
}
