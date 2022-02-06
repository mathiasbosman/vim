package be.mathiasbosman.vim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VimApplication {

    public static void main(String[] args) {
        SpringApplication.run(VimApplication.class, args);
    }

}
