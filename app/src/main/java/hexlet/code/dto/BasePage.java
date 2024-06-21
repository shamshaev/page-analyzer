package hexlet.code.dto;

public class BasePage {
    private String flash;

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }

    public static String getType(String flash) throws RuntimeException {
        return switch (flash) {
            case "Страница успешно добавлена", "Страница успешно проверена" -> "success";
            case "Некорректный URL", "Некорректный адрес" -> "danger";
            case "Страница уже существует" -> "info";
            default -> throw new RuntimeException("Unknown alert: '" + flash + "'");
        };
    }

}
