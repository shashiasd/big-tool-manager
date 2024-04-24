package com.rental.tool.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * Finds out and returns list of holidays in a given year
 */
@Service
public class HolidayService {

    /**
     * Return a list of holiday in the given year
     * @param year For which list of holidays is required
     * @return List of holiday dates
     */
    public List<LocalDate> getHolidays(int year) {
        LocalDate independenceDay = getIndependenceDay(year);
        LocalDate laborDay = getLaborDay(year);
        return List.of(laborDay, independenceDay);
    }

    /**
     * @param year for which labor day is required
     * @return labor day date
     */
    private LocalDate getLaborDay(int year) {
        return LocalDate.of(year, Month.SEPTEMBER, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }

    /**
     * @param year for which independence day is required
     * @return independence day date
     */
    private LocalDate getIndependenceDay(int year) {
        LocalDate independenceDay = LocalDate.of(year, Month.JULY, 4);
        if (independenceDay.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            independenceDay = independenceDay.plusDays(1);
        }
        return independenceDay;
    }

}
