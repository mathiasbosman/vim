package be.mathiasbosman.vim.security.keycloak;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloakConfig {

  @Bean
  public KeycloakSpringBootConfigResolver keyCloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }
}
