package com.solutions.mongekantorovich.controllers;

import com.solutions.mongekantorovich.dto.RequestDto;
import com.solutions.mongekantorovich.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController implements ITaskController {
    private final TaskService taskService;

    @Override
    @GetMapping
    public ResponseEntity<?> solve(@RequestBody RequestDto requestDto) {
        return taskService.solve(
                requestDto.getProducers(),
                requestDto.getConsumers(),
                requestDto.getCosts(),
                requestDto.getMethod()
        );
    }
}
