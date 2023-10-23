package com.solutions.mongekantorovich.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
public class PotentialsSolution {
    private List<Long> u;
    private List<Long> v;
    private List<List<Long>> plan;
    private List<List<Long>> tablePotentials;

    public PotentialsSolution(
            List<List<Long>> plan
    ){
        this.plan = plan;

        u = new ArrayList<>(Collections.nCopies(plan.size(), null));
        v = new ArrayList<>(Collections.nCopies(plan.get(0).size(), null));

        tablePotentials = new ArrayList<>();
        for (int i = 0; i < plan.size(); i++) {
            tablePotentials.add(
                    new ArrayList<>(Collections.nCopies(plan.get(0).size(), 0L))
            );
        }
    }
}
