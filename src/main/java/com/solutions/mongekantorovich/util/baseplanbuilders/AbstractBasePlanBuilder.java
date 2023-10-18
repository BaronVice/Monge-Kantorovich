package com.solutions.mongekantorovich.util.baseplanbuilders;

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
    protected boolean isGood;

    public AbstractBasePlanBuilder(
            List<Long> producers,
            List<Long> consumers,
            List<List<Long>> costs
    ){
        isGood = true;
        this.producers = new ArrayList<>(producers);
        this.consumers = new ArrayList<>(consumers);

        basePlan = new ArrayList<>(
                Collections.nCopies(
                        costs.size(),
                        new ArrayList<>(
                                Collections.nCopies(
                                        costs.get(0).size(),
                                        -1L
                                )
                        )
                )
        );
    }

    public abstract void buildBasePlan();
    public abstract void stabilize();
}
