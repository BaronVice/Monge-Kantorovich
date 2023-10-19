package com.solutions.mongekantorovich.util.baseplanbuilders;

import java.util.List;

public class FogelBasePlanBuilder extends AbstractBasePlanBuilder {
    public FogelBasePlanBuilder(
            List<Long> producers,
            List<Long> consumers,
            List<List<Long>> costs
    ){
        super(
                producers,
                consumers,
                costs
        );
    }

    @Override
    public void buildBasePlan() {

    }

}
