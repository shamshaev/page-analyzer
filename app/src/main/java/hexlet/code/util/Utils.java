package hexlet.code.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {
    public static String formatTime(Timestamp time) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatter.format(time);
    }
}
