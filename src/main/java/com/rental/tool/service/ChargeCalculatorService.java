package com.rental.tool.service;

import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
public class ChargeCalculatorService {

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    /**
     *
     * @param numberOfChargeDays count of chargeable days
     * @param dailyCharge per day charge for a tool
     * @return pre discount charge rounded off to 2 decimal points
     */
    public double calculatePreDiscountCharge(int numberOfChargeDays, double dailyCharge) {
        return roundToHalfCents(numberOfChargeDays * dailyCharge);
    }

    private double roundToHalfCents(double value) {
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return Double.parseDouble(decimalFormat.format(value));
    }

    /**
     *
     * @param discount discount percentage requested by a clerk
     * @param preDiscountAmount amount before applying the discount
     * @return discount amount
     */
    public double calculateDiscountAmount(int discount, double preDiscountAmount) {
        return roundToHalfCents(preDiscountAmount * ((double) discount / 100));
    }

    /**
     *
     * @param preDiscountedCharge Amount before applying the discount
     * @param discountAmount Discounted amount
     * @return Final charge
     */
    public double calculateFinalCharge(double preDiscountedCharge, double discountAmount) {
        return roundToHalfCents(preDiscountedCharge - discountAmount);
    }

}
