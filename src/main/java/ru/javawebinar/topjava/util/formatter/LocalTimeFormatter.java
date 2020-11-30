package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String formatted, Locale locale) throws ParseException {
        return DateTimeUtil.parseLocalTime(formatted);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return null;
    }
}
