package be.mathiasbosman.vim.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Simple record of the server info.
 */
public record ServerInfoDto(LocalDateTime time, boolean running) {

}
