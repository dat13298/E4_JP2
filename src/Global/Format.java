package Global;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Format {
    public static LocalDateTime convertStringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd H:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
