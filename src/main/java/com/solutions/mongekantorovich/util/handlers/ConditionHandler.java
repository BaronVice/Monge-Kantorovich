package com.solutions.mongekantorovich.util.handlers;

import com.solutions.mongekantorovich.util.Method;
import com.solutions.mongekantorovich.util.baseplanbuilders.AbstractBasePlanBuilder;
import com.solutions.mongekantorovich.util.baseplanbuilders.CornerBasePlanBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class to handle special conditions
 */
public class ConditionHandler {

    /**
     * Balances producers sum and consumer sum if it is required
     * @param producers list of producers
     * @param consumers list of consumers
     * @param costs table of costs
     * @return suppose that producers sum is <strong>A</strong> and consumers sum is <strong>B</strong>.<br/>
     * if A > B: 1,<br/>
     * if A < B: -1,<br/>
     * if A == B: 0.
     */
    public static int handleProducersConsumers(
            List<Long> producers,
            List<Long> consumers,
            List<List<Long>> costs
    ){
        long producersSum = producers.stream().mapToLong(Long::longValue).sum();
        long consumersSum = consumers.stream().mapToLong(Long::longValue).sum();
        long difference = producersSum - consumersSum;

        if (difference > 0){
            consumers.add(difference);
            costsAppender(costs, false);
            return 1;
        } if (difference < 0){
            producers.add(-difference);
            costsAppender(costs, true);
            return -1;
        }

        return 0;
    }

    /**
     * Append additional producer/consumer in costs table
     * @param costs table of costs
     * @param appendProducer if true - append producer, otherwise append consumer
     */
    private static void costsAppender(
            List<List<Long>> costs,
            boolean appendProducer
    ){
        if (appendProducer){
            costs.add(new ArrayList<>(
                    Collections.nCopies(costs.get(0).size(), 0L))
            );

            return;
        }

        for (List<Long> producer : costs) {
            producer.add(0L);
        }
    }

    /**
     * Build base plan using one of the methods
     * @param producers list of producers
     * @param consumers list of consumers
     * @param costs table of costs
     * @param method given {@link Method method}
     * @return base plan
     */
    public static AbstractBasePlanBuilder handleMethod(
            List<Long> producers,
            List<Long> consumers,
            List<List<Long>> costs,
            Method method
    ){
        AbstractBasePlanBuilder planBuilder;
        switch (method){
            case CORNER -> planBuilder = new CornerBasePlanBuilder(
                    producers,
                    consumers,
                    costs
            );
            case FOGEL, MIN_COST -> throw new RuntimeException("Method is not implemented");
            default -> throw new RuntimeException("Method does not exist");
        }

        planBuilder.buildBasePlan();
        return planBuilder;
    }
}
