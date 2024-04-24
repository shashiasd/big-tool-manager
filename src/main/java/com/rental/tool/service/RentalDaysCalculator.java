package com.rental.tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class RentalDaysCalculator {

    @Autowired
    private HolidayService holidayService;

    /**
     * Calculates Charge Rental Days excluding weekends and holidays depending on the configuration
     * @param startDate - Start Date or checkout date of the agreement
     * @param numberOfDays - Number of days to rent a tool excluding start date
     * @param weekendCharge - Whether to charge on weekend
     * @param holidayCharge - Whether to charge on holiday
     * @return count of rental days which needs to be charged
     */
    public long calculateRentalDays(LocalDate startDate, int numberOfDays, boolean weekendCharge,
                                   boolean holidayCharge) {
        //Because start is not included in the rental agreement
        startDate = startDate.plusDays(1);
        List<Predicate<LocalDate>> predicates = new ArrayList<>();
        //whether to charge for a weekend
        //If true, it will be free of cost for the day
        if (!weekendCharge) {
            Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                    || date.getDayOfWeek() == DayOfWeek.SUNDAY;
            predicates.add(isWeekend);
        }
        //whether to charge for a holiday
        //If true, it will be free of cost for the day
        if (!holidayCharge) {
            List<LocalDate> holidays = holidayService.getHolidays(startDate.getYear());
            predicates.add(holidays::contains);
        }
        //Counts number of chargeable days based on request configuration for holiday and weekend
        return startDate.datesUntil(startDate.plusDays(numberOfDays))
                .filter(predicates.stream().reduce(Predicate::or).orElse(x -> false).negate())
                .count();
    }

}
