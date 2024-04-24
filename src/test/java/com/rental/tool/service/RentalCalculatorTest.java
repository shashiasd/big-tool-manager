package com.rental.tool.service;

import com.rental.tool.datagenerator.ToolDataGenerator;
import com.rental.tool.dto.CheckoutRequest;
import com.rental.tool.dto.RentalAgreement;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class RentalCalculatorTest {

    @Autowired
    ToolDataGenerator toolDataGenerator;

    @Autowired
    DataFormatter dataFormatter;

    @Autowired
    private CheckoutService checkoutService;

    private ValidatorFactory factory;

    private Validator validator;

    @BeforeEach
    void setUp() {
        toolDataGenerator.generateData();
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void cleanUp() {
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    public void test1() {
        //Given
        CheckoutRequest checkoutRequest = new CheckoutRequest("JAKR",5,
                101, dataFormatter.parseToLocalDate("9/3/15"));

        //When
        Set<ConstraintViolation<CheckoutRequest>> violations = validator.validate(checkoutRequest);

        //Then
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void test2() {
        //Given
        CheckoutRequest checkoutRequest = new CheckoutRequest("LADW",3,
                10, dataFormatter.parseToLocalDate("7/2/20"));

        //When
        Set<ConstraintViolation<CheckoutRequest>> violations = validator.validate(checkoutRequest);
        RentalAgreement rentalAgreement = checkoutService.checkout(checkoutRequest);

        //Then
        Assertions.assertTrue(violations.isEmpty());
        Assertions.assertEquals(2, rentalAgreement.getChargeDays());
        Assertions.assertEquals(1.99, rentalAgreement.getDailyRentalCharge());
        Assertions.assertEquals(3.98, rentalAgreement.getPreDiscountedCharge());
        Assertions.assertEquals(0.40, rentalAgreement.getDiscountAmount());
        Assertions.assertEquals(3.58, rentalAgreement.getFinalCharge());
    }

    @Test
    void test3() {
        //Given
        CheckoutRequest checkoutRequest = new CheckoutRequest("CHNS",5,
                25, dataFormatter.parseToLocalDate("7/2/15"));

        //When
        Set<ConstraintViolation<CheckoutRequest>> violations = validator.validate(checkoutRequest);
        RentalAgreement rentalAgreement = checkoutService.checkout(checkoutRequest);

        //Then
        Assertions.assertTrue(violations.isEmpty());
        Assertions.assertEquals(3, rentalAgreement.getChargeDays());
        Assertions.assertEquals(1.49, rentalAgreement.getDailyRentalCharge());
        Assertions.assertEquals(4.47, rentalAgreement.getPreDiscountedCharge());
        Assertions.assertEquals(1.12, rentalAgreement.getDiscountAmount());
        Assertions.assertEquals(3.35, rentalAgreement.getFinalCharge());
    }

    @Test
    void test4() {
        //Given
        CheckoutRequest checkoutRequest = new CheckoutRequest("JAKD",6,
                0, dataFormatter.parseToLocalDate("9/3/15"));

        //When
        Set<ConstraintViolation<CheckoutRequest>> violations = validator.validate(checkoutRequest);
        RentalAgreement rentalAgreement = checkoutService.checkout(checkoutRequest);

        //Then
        Assertions.assertTrue(violations.isEmpty());
        Assertions.assertEquals(3, rentalAgreement.getChargeDays());
        Assertions.assertEquals(2.99, rentalAgreement.getDailyRentalCharge());
        Assertions.assertEquals(8.97, rentalAgreement.getPreDiscountedCharge());
        Assertions.assertEquals(0, rentalAgreement.getDiscountAmount());
        Assertions.assertEquals(8.97, rentalAgreement.getFinalCharge());
    }

    @Test
    void test5() {
        //Given
        CheckoutRequest checkoutRequest = new CheckoutRequest("JAKR",9,
                0, dataFormatter.parseToLocalDate("7/2/15"));

        //When
        Set<ConstraintViolation<CheckoutRequest>> violations = validator.validate(checkoutRequest);
        RentalAgreement rentalAgreement = checkoutService.checkout(checkoutRequest);

        //Then
        Assertions.assertTrue(violations.isEmpty());
        Assertions.assertEquals(5, rentalAgreement.getChargeDays());
        Assertions.assertEquals(2.99, rentalAgreement.getDailyRentalCharge());
        Assertions.assertEquals(14.96, rentalAgreement.getPreDiscountedCharge());
        Assertions.assertEquals(0, rentalAgreement.getDiscountAmount());
        Assertions.assertEquals(14.96, rentalAgreement.getFinalCharge());
    }

    @Test
    void test6() {
        //Given
        CheckoutRequest checkoutRequest = new CheckoutRequest("JAKR",4,
                50, dataFormatter.parseToLocalDate("7/2/20"));

        //When
        Set<ConstraintViolation<CheckoutRequest>> violations = validator.validate(checkoutRequest);
        RentalAgreement rentalAgreement = checkoutService.checkout(checkoutRequest);

        //Then
        Assertions.assertTrue(violations.isEmpty());
        Assertions.assertEquals(1, rentalAgreement.getChargeDays());
        Assertions.assertEquals(2.99, rentalAgreement.getDailyRentalCharge());
        Assertions.assertEquals(2.99, rentalAgreement.getPreDiscountedCharge());
        Assertions.assertEquals(1.50, rentalAgreement.getDiscountAmount());
        Assertions.assertEquals(1.50, rentalAgreement.getFinalCharge());
    }
}