package ko.notePad.planner.util;

import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss");
    }
}
