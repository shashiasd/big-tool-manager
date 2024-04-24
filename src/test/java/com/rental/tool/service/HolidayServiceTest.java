package com.rental.tool.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootTest
class HolidayServiceTest {

    @Autowired
    HolidayService holidayService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnHolidaysWithDecrement() {
        LocalDate localDate = LocalDate.of(2020, Month.APRIL, 9);
        List<LocalDate> holidays = holidayService.getHolidays(localDate.getYear());
        Assertions.assertTrue(holidays.contains(LocalDate.of(2020, Month.SEPTEMBER, 7)));
        Assertions.assertTrue(holidays.contains(LocalDate.of(2020, Month.JULY, 3)));
    }

    @Test
    void shouldReturnHolidaysWithIncrement() {
        LocalDate localDate = LocalDate.of(2021, Month.APRIL, 9);
        List<LocalDate> holidays = holidayService.getHolidays(localDate.getYear());
        Assertions.assertTrue(holidays.contains(LocalDate.of(2021, Month.SEPTEMBER, 6)));
        Assertions.assertTrue(holidays.contains(LocalDate.of(2021, Month.JULY, 5)));
    }

    @Test
    void shouldReturnHolidaysWithoutIncrementOrDecrement() {
        LocalDate localDate = LocalDate.of(2024, Month.APRIL, 9);
        List<LocalDate> holidays = holidayService.getHolidays(localDate.getYear());
        Assertions.assertTrue(holidays.contains(LocalDate.of(2024, Month.SEPTEMBER, 2)));
        Assertions.assertTrue(holidays.contains(LocalDate.of(2024, Month.JULY, 4)));
    }
}