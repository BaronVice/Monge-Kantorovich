package com.solutions.mongekantorovich.services;

import com.solutions.mongekantorovich.dto.RequestDto;
import com.solutions.mongekantorovich.dto.ResponseDto;
import com.solutions.mongekantorovich.util.Method;
import com.solutions.mongekantorovich.util.handlers.ConditionHandler;
import com.solutions.mongekantorovich.util.handlers.ResponseDtoHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    public ResponseEntity<?> solve(
            List<Long> producers,
            List<Long> consumers,
            List<List<Long>> costs,
            Method method
    ) {
        ResponseDto responseDto = new ResponseDto();

        int closedStatus = ConditionHandler.handleProducersConsumers(
                producers, consumers, costs
        );
        ResponseDtoHandler.setClosedStatus(
                responseDto, closedStatus
        );



        return ResponseEntity.ok(
                new RequestDto(
                        producers,
                        consumers,
                        costs,
                        method
                )
        );
    }
}
