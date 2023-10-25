package com.solutions.mongekantorovich.util.handlers;

import com.solutions.mongekantorovich.dto.ResponseDto;
import com.solutions.mongekantorovich.util.containers.PotentialsSolution;
import com.solutions.mongekantorovich.util.baseplanbuilders.AbstractBasePlanBuilder;

import java.util.List;

/**
 * Utility class that forms ResponseDto
 */
public class ResponseDtoHandler {

    /**
     * Forms response based on given values
     * @param closedStatus task status
     * @param planBuilder base plan and values related to it
     * @param potentialsSolutions solutions calculated with potential method
     * @return formed response
     */
    public static ResponseDto formResponse(
        int closedStatus,
        AbstractBasePlanBuilder planBuilder,
        List<PotentialsSolution> potentialsSolutions
    ){
        ResponseDto responseDto = new ResponseDto();
        setClosedStatus(
                responseDto, closedStatus
        );
        setBasePlan(
                responseDto, planBuilder
        );
        setPotentialSolutions(
                responseDto, potentialsSolutions
        );

        return responseDto;
    }

    /**
     * Assign task status in given response
     * @param responseDto response to handle
     * @param status task status formed in {@link ConditionHandler#handleProducersConsumers handleProducersConsumers}
     */
    private static void setClosedStatus(ResponseDto responseDto, int status){
        responseDto.setIsClosed(
                switch (status){
                    case 1 -> "Opened: additional consumer required";
                    case -1 -> "Opened: additional producer required";
                    default -> "Closed";
                }
        );
    }

    /**
     * Assign base plan status in given response
     * @param responseDto response to handle
     * @param planBuilder base plan and values related to it
     */
    private static void setBasePlan(
            ResponseDto responseDto,
            AbstractBasePlanBuilder planBuilder
    ) {
        responseDto.setMnMinusOneCondition(planBuilder.isGood());
        responseDto.setBasePlan(planBuilder.getBasePlan());
        responseDto.setBasicCellsCoordinates(planBuilder.getBasicCellsCoordinates());
    }

    /**
     * Assign solutions calculated by potential method in given response
     * @param responseDto response to handle
     * @param potentialsSolutions calculated solutions
     */
    private static void setPotentialSolutions(
            ResponseDto responseDto,
            List<PotentialsSolution> potentialsSolutions
    ) {
        responseDto.setPotentialsSolutions(potentialsSolutions);
    }
}
