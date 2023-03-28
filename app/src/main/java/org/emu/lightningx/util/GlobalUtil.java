package org.emu.lightningx.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GlobalUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static String getCurrentDateFormatted() {
        return LocalDateTime.now().format(formatter);
    }
}
