package com.solutions.mongekantorovich.services;

import com.solutions.mongekantorovich.dto.ResponseDto;
import com.solutions.mongekantorovich.util.Method;
import com.solutions.mongekantorovich.util.baseplanbuilders.AbstractBasePlanBuilder;
import com.solutions.mongekantorovich.util.handlers.ConditionHandler;
import com.solutions.mongekantorovich.util.handlers.ResponseDtoHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    public ResponseEntity<ResponseDto> solve(
            List<Long> producers,
            List<Long> consumers,
            List<List<Long>> costs,
            Method method
    ) {
        ResponseDto responseDto = new ResponseDto();

        int closedStatus = ConditionHandler.handleProducersConsumers(
                producers, consumers, costs
        );
        AbstractBasePlanBuilder planBuilder = ConditionHandler.methodHandler(
                producers, consumers, costs, method
        );


        ResponseDtoHandler.setClosedStatus(
                responseDto, closedStatus
        );
        ResponseDtoHandler.setBasePlan(
                responseDto, planBuilder
        );

        return ResponseEntity.ok(responseDto);
    }
}
