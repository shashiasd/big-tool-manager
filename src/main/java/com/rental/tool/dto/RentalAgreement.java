package com.rental.tool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rental.tool.service.DataFormatter;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RentalAgreement {
    private String toolCode;
    private String toolType;
    private String brand;
    private long rentalDays;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yy")
    private LocalDate checkoutDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yy")
    private LocalDate dueDate;
    private double dailyRentalCharge;
    //Count of chargeable days, from day after checkout through and including due date
    private int chargeDays;
    private double preDiscountedCharge;
    private int discountPercent;
    //Calculated from discount % and pre-discount charge. Resulting amount rounded half up to cents
    private double discountAmount;
    //Calculated as pre-discount charge - discount amount
    private double finalCharge;

    @Override
    public String toString() {
        return "\n Tool code: '" + toolCode + '\'' +
                "\n Tool type: " + toolType +
                "\n Tool brand: '" + brand + '\'' +
                "\n Rental days: " + rentalDays +
                "\n Check out date: " + DataFormatter.formatLocalDate(checkoutDate) +
                "\n Due date: " + DataFormatter.formatLocalDate(dueDate) +
                "\n Daily rental charge: " + DataFormatter.formatCurrency(dailyRentalCharge) +
                "\n Charge days: " + chargeDays +
                "\n Pre-discount charge: " + DataFormatter.formatCurrency(preDiscountedCharge) +
                "\n Discount percent: " + discountPercent + "%" +
                "\n Discount amount: " + DataFormatter.formatCurrency(discountAmount) +
                "\n Final charge: " + DataFormatter.formatCurrency(finalCharge);
    }
}
