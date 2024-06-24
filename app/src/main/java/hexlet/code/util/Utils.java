package hexlet.code.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String formatTime(Instant time) {
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());
        return formatter.format(time);
    }

    public static String getFlashType(String flash) throws RuntimeException {
        return switch (flash) {
            case "Страница успешно добавлена", "Страница успешно проверена" -> "success";
            case "Некорректный URL", "Некорректный адрес" -> "danger";
            case "Страница уже существует" -> "info";
            default -> throw new RuntimeException("Unknown alert: '" + flash + "'");
        };
    }
}
