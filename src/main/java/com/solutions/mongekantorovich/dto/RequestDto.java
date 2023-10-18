package com.solutions.mongekantorovich.dto;

import com.solutions.mongekantorovich.util.Method;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestDto {
    private List<Long> producers;
    private List<Long> consumers;
    private List<List<Long>> costs;
    private Method method;
}
