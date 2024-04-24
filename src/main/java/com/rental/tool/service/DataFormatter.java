package com.rental.tool.service;

import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class DataFormatter {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
    final static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yy");

    public LocalDate parseToLocalDate(String date) {
        return  LocalDate.parse(date, formatter);
    }

    public static String formatLocalDate(LocalDate date) {
        return  date.format(formatter2);
    }

    /**
     *
     * @param amount to format
     * @return US currency format e.g. $9,999.99
     */
    public static String formatCurrency(double amount) {
        // Create a new Locale
        Locale usa = new Locale("en", "US");
        // Create a formatter given the Locale
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(amount);
    }
}
