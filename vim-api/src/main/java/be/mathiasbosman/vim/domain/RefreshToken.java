package be.mathiasbosman.vim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Identifiable<UUID> {

  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  private UUID token;

  private Instant expiresAt;
}
