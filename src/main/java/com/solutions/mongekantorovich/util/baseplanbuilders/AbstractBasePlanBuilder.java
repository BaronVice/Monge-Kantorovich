package com.solutions.mongekantorovich.util.baseplanbuilders;

import com.solutions.mongekantorovich.util.containers.Pair;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractBasePlanBuilder {
    protected List<Long> producers;
    protected List<Long> consumers;
    protected List<List<Long>> basePlan;
    protected List<Pair> basicCellsCoordinates;
    protected boolean isGood;

    public AbstractBasePlanBuilder(
            List<Long> producers,
            List<Long> consumers,
            List<List<Long>> costs
    ){
        isGood = true;
        basicCellsCoordinates = new ArrayList<>();
        this.producers = new ArrayList<>(producers);
        this.consumers = new ArrayList<>(consumers);

        basePlan = new ArrayList<>();
        for (int i = 0; i < costs.size(); i++) {
            basePlan.add(new ArrayList<>(Collections.nCopies(
                    costs.get(0).size(), -1L
            )));
        }
    }

    public abstract void buildBasePlan();
}
