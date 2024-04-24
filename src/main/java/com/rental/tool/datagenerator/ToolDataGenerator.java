package com.rental.tool.datagenerator;

import com.rental.tool.entities.BigTool;
import com.rental.tool.entities.ToolType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ToolDataGenerator {

    static Map<String, BigTool> bigTools;

    /**
     * Generates static data in memory
     */
    public void generateData() {
        if (bigTools == null) {
            BigTool bigTool1 = new BigTool("CHNS", ToolType.CHAINSAW, "Stihl");
            BigTool bigTool2 = new BigTool("LADW", ToolType.LADDER, "Werner");
            BigTool bigTool3 = new BigTool("JAKD", ToolType.JACKHAMMER, "DeWalt");
            BigTool bigTool4 = new BigTool("JAKR", ToolType.JACKHAMMER, "Ridgid");
            List<BigTool> bigToolsList = Arrays.asList(bigTool1, bigTool2, bigTool3, bigTool4);
            bigTools = bigToolsList.stream().collect(Collectors.toMap(BigTool::getToolCode, bigTool -> bigTool));
        }
    }

    /**
     * Returns a BigTool object based on toolCode
     * @param toolCode Unique code of the tool
     * @return A big tool object
     */
    public BigTool getByCode(String toolCode) {
        return bigTools.get(toolCode);
    }
}
