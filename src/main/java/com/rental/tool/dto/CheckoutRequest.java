package com.rental.tool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {
    @NotNull
    @NotEmpty
    private String toolCode;
    @Min(value = 1, message = "Rental day count must be greater than or equal to 1")
    private int rentalDayCount;
    @Min(value = 0, message = "Discount percent must be greater than or equal to 0")
    @Max(value = 100, message = "Discount percent must be less than or equal to 100")
    private int discountPercent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/yy")
    private LocalDate checkoutDate;
}
