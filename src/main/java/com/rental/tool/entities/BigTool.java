package com.rental.tool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigTool {
    private String toolCode;
    private ToolType toolType;
    private String brand;
}