package be.mathiasbosman.vim.security;

import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class VimPermissionEvaluator implements PermissionEvaluator {


  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject,
      Object permission) {
    if (authentication == null || targetDomainObject == null || !(permission instanceof String)) {
      return false;
    }

    String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();

    return hasPrivilege(authentication, targetType, permission.toString().toUpperCase());
  }

  @Override
  public boolean hasPermission(
      Authentication auth, Serializable targetId, String targetType, Object permission) {
    if (auth == null || targetType == null || !(permission instanceof String)) {
      return false;
    }
    return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
  }

  private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
    return auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .anyMatch(authority -> authority.startsWith(targetType) && authority.contains(permission));
  }
}
