package mimmey.codeSharingPlatform.business;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManipulations {
    private final static String DATE_FORMATTER = "yyyy/MM/dd HH:mm:ss.SSSSSSSSS";

    public static String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return date.format(formatter);
    }

    public static LocalDateTime unformatDate(String formattedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return LocalDateTime.parse(formattedDate, formatter);
    }

    public static int differenceInSeconds(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return (int) Duration.between(localDateTime1, localDateTime2).getSeconds();
    }
}
