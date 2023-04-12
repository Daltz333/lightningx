package org.emu.lightningx.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GlobalUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static String getCurrentDateFormatted() {
        String date = LocalDateTime.now().format(formatter);
        String[] splitDate = date.split("/");

        // DateTimeFormatter doesn't support syntax where the first number in month/day is 0
        // For example, there is no way to format 03/05/2020 into 3/5/2020
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < splitDate.length; i++) {
            if (splitDate[i].charAt(0) == '0') {
                builder.append(splitDate[i].charAt(1));
            } else {
                builder.append(splitDate[i]);
            }

            // Don't append / if we're on the last substr
            if (i != splitDate.length - 1) {
                builder.append("/");
            }
        }

        return builder.toString();
    }
}
