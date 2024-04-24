package com.rental.tool.controller;

import com.rental.tool.dto.CheckoutRequest;
import com.rental.tool.dto.RentalAgreement;
import com.rental.tool.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller to handle rent requests
 */
@RestController
@Validated
public class RentController {

    @Autowired
    private CheckoutService checkoutService;

    /**
     * API method to handle /checkout API
     * @param checkoutRequest - Contains toolCode, rentalDayCount, discountPercent and checkoutDate
     *                        Validates the request
     * @return RentalAgreement with details like final charge and rental charge days count
     */
    @PostMapping("/checkout")
    public ResponseEntity<RentalAgreement> checkout(@Valid @RequestBody CheckoutRequest checkoutRequest) {
        return new ResponseEntity<>(checkoutService.checkout(checkoutRequest), HttpStatus.OK);
    }

    /**
     * An exception handler for MethodArgumentNotValidException.class
     * It'll be called when any one or more arguments are not valid
     * in the checkout request
     * @param ex Exception Instance of failed method argument validation
     * @return A list of validation errors for failed arguments
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
