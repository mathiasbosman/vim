package be.mathiasbosman.vim.domain;

import java.time.LocalDateTime;

/**
 * Simple record of the server info.
 */
public record ServerInfoDto(LocalDateTime time, boolean running) {

}
