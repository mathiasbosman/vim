package be.mathiasbosman.vim.security;

import be.mathiasbosman.vim.domain.User;
import be.mathiasbosman.vim.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VimUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    return userRepository.getByUsername(username)
        .map(this::mapUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
  }

  private UserDetails mapUserDetails(@NonNull User user) {
    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities(getPermissions(user.getRoles()));
      }

      @Override
      public String getPassword() {
        return user.getPassword();
      }

      @Override
      public String getUsername() {
        return user.getUsername();
      }

      @Override
      public boolean isAccountNonExpired() {
        return !user.isDeleted();
      }

      @Override
      public boolean isAccountNonLocked() {
        return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return !user.isDeleted();
      }
    };
  }

  private List<String> getPermissions(Collection<Role> roles) {
    List<String> permissions = new ArrayList<>();
    List<Permission> collection = new ArrayList<>();
    roles.forEach(r -> {
      permissions.add(SecurityContext.Role.PREFIX + r.getName());
      collection.addAll(r.getPermissions());
    });
    collection.forEach(p -> permissions.add(p.getName()));
    return permissions;
  }

  private List<SimpleGrantedAuthority> getGrantedAuthorities(List<String> permissions) {
    return permissions.stream()
        .map(SimpleGrantedAuthority::new)
        .toList();
  }
}
