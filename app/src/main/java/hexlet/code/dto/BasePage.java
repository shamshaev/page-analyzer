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
            case "Некорректный URL" -> "danger";
            case "Страница уже существует" -> "info";
            case "Страница успешно добавлена" -> "success";
            default -> throw new RuntimeException("Unknown alert: '" + flash + "'");
        };
    }

}
