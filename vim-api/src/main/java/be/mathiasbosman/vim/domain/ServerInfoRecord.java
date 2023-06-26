package be.mathiasbosman.vim.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Simple record of the server info.
 */
public record ServerInfoRecord(
    LocalDateTime time,
    ZoneId zoneId,
    boolean running) {

}
