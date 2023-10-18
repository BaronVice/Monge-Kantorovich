package com.solutions.mongekantorovich.util.baseplanbuilders;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CornerBasePlanBuilder extends AbstractBasePlanBuilder {
    public CornerBasePlanBuilder(
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
        int basicCells = 0;

        int producer = -1;
        int consumer = -1;
        int total = producers.size() + consumers.size()-2;

        long producerValue = 0;
        long consumerValue = 0;
        for (; producer + consumer != total; basicCells++){
            if (producerValue == 0)
                producerValue = producers.get(++producer);
            if (consumerValue == 0)
                consumerValue = consumers.get(++consumer);

            long value = Math.min(
                    producerValue,
                    consumerValue
            );
            basePlan.get(producer).set(consumer, value);

            producerValue -= value;
            consumerValue -= value;
        }

        if (basicCells == basePlan.size() + basePlan.get(0).size() - 1)
            return;

        stabilize();
        isGood = false;
    }

    @Override
    public void stabilize() {
        int producer = 0;
        int consumer = 0;

        int producers = basePlan.size()-1;
        int consumers = basePlan.get(0).size()-1;
        while (producer != producers && consumer != consumers){
            if (basePlan.get(producer).get(consumer+1) != -1){
                consumer++;
            } else if (basePlan.get(producer+1).get(consumer) != -1) {
                producer++;
            } else {
                basePlan.get(producer+1).set(consumer, 0L);
                consumer++;
                producer++;
            }
        }
    }
}
