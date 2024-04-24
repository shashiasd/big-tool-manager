package com.rental.tool.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ToolType {

    LADDER (1.99, true, true, false),
    CHAINSAW (1.49, true, false, true),
    JACKHAMMER (2.99, true, false, false);

    private final double dailyCharge;
    private final boolean weekdayCharge;
    private final boolean weekendCharge;
    private final boolean holidayCharge;

}
