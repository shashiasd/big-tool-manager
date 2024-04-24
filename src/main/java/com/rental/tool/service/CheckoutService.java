package com.rental.tool.service;

import com.rental.tool.datagenerator.ToolDataGenerator;
import com.rental.tool.dto.CheckoutRequest;
import com.rental.tool.dto.RentalAgreement;
import com.rental.tool.entities.BigTool;
import com.rental.tool.entities.ToolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Checkout service to generate the final rent agreement
 */
@Service
public class CheckoutService {

    @Autowired
    private ToolDataGenerator toolDataGenerator;

    @Autowired
    private RentalDaysCalculator rentalDaysCalculator;

    @Autowired
    private ChargeCalculatorService chargeCalculatorService;

    Logger logger = LoggerFactory.getLogger(CheckoutService.class);

    /**
     * It calculates rental days based on holidays and weekends
     * And based on discount percent, per day charge for a tool and
     * rental charge days count, generates final charge
     * @param checkoutRequest - Contains toolCode, rentalDayCount, discountPercent and checkoutDate
     * @return RentalAgreement with details like final charge and rental charge days count
     */
    public RentalAgreement checkout(CheckoutRequest checkoutRequest) {
        //Generates static data in memory
        toolDataGenerator.generateData();
        BigTool bigTool = toolDataGenerator.getByCode(checkoutRequest.getToolCode());
        ToolType toolType = bigTool.getToolType();
        int days = (int) rentalDaysCalculator.calculateRentalDays(checkoutRequest.getCheckoutDate(),
                checkoutRequest.getRentalDayCount(), toolType.isWeekendCharge(), toolType.isHolidayCharge());
        double preDiscountCharge =  chargeCalculatorService.calculatePreDiscountCharge(days, toolType.getDailyCharge());
        double discountAmount = chargeCalculatorService.calculateDiscountAmount(checkoutRequest.getDiscountPercent(),
                preDiscountCharge);
        double finalCharge = chargeCalculatorService.calculateFinalCharge(preDiscountCharge, discountAmount);

        //Generate rental agreement
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setToolCode(checkoutRequest.getToolCode());
        rentalAgreement.setToolType(toolType.name());
        rentalAgreement.setBrand(bigTool.getBrand());
        rentalAgreement.setRentalDays(checkoutRequest.getRentalDayCount());
        rentalAgreement.setCheckoutDate(checkoutRequest.getCheckoutDate());
        rentalAgreement.setDueDate(checkoutRequest.getCheckoutDate().plusDays(checkoutRequest.getRentalDayCount()));
        rentalAgreement.setDailyRentalCharge(bigTool.getToolType().getDailyCharge());
        rentalAgreement.setChargeDays(days);
        rentalAgreement.setPreDiscountedCharge(preDiscountCharge);
        rentalAgreement.setDiscountPercent(checkoutRequest.getDiscountPercent());
        rentalAgreement.setDiscountAmount(discountAmount);
        rentalAgreement.setFinalCharge(finalCharge);
        logger.info(rentalAgreement.toString());
        return rentalAgreement;
    }

}
