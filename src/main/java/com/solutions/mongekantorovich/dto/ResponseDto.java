package com.solutions.mongekantorovich.dto;

import com.solutions.mongekantorovich.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseDto {
    private String isClosed;
    private boolean mnMinusOneCondition;
    private List<List<Long>> basePlan;
    private List<Pair> basicCellsCoordinates;
}
