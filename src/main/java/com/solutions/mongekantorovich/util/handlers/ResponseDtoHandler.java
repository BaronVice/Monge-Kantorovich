package com.solutions.mongekantorovich.util.handlers;

import com.solutions.mongekantorovich.dto.ResponseDto;
import com.solutions.mongekantorovich.util.containers.PotentialsSolution;
import com.solutions.mongekantorovich.util.baseplanbuilders.AbstractBasePlanBuilder;

import java.util.List;

public class ResponseDtoHandler {
    public static void setClosedStatus(ResponseDto responseDto, int status){
        responseDto.setIsClosed(
                switch (status){
                    case 1 -> "Opened: additional consumer required";
                    case -1 -> "Opened: additional producer required";
                    default -> "Closed";
                }
        );
    }

    public static void setBasePlan(
            ResponseDto responseDto,
            AbstractBasePlanBuilder planBuilder
    ) {
        responseDto.setMnMinusOneCondition(planBuilder.isGood());
        responseDto.setBasePlan(planBuilder.getBasePlan());
        responseDto.setBasicCellsCoordinates(planBuilder.getBasicCellsCoordinates());
    }

    public static void setPotentialSolutions(
            ResponseDto responseDto,
            List<PotentialsSolution> potentialsSolutions
    ) {
        responseDto.setPotentialsSolutions(potentialsSolutions);
    }
}
