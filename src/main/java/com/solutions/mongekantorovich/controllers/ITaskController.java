package com.solutions.mongekantorovich.controllers;

import com.solutions.mongekantorovich.dto.RequestDto;
import org.springframework.http.ResponseEntity;

public interface ITaskController {
    public ResponseEntity<?> solve(RequestDto requestDto);
}
