package be.mathiasbosman.vim.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Custom Keycloak configuration Bean.
 */
@Configuration
public class KeycloakConfig {

  @Bean
  public KeycloakSpringBootConfigResolver keyCloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }
}
