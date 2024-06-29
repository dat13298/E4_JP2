package Global;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Format {
    public static LocalDateTime convertStringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd H:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
    public static LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
        return LocalDate.parse(date, formatter);
    }
}
