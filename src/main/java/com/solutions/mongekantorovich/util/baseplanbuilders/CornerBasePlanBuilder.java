package com.solutions.mongekantorovich.util.baseplanbuilders;

import com.solutions.mongekantorovich.util.containers.Pair;
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
        int producer = 0;
        int consumer = 0;
        long producerValue = producers.get(0);
        long consumerValue = consumers.get(0);
        int total = producers.size() + consumers.size()-2;

        while (producer + consumer != total){
            if (producerValue == 0 && consumerValue == 0)
                addZeroCell(producer, consumer);
            if (producerValue == 0)
                producerValue = producers.get(++producer);
            if (consumerValue == 0)
                consumerValue = consumers.get(++consumer);

            long value = Math.min(
                    producerValue,
                    consumerValue
            );
            setBasicCell(producer, consumer, value);

            producerValue -= value;
            consumerValue -= value;
        }
    }
    private void addZeroCell(int producer, int consumer){
        isGood = false;
        setBasicCell(producer+1, consumer, 0L);
    }

    private void setBasicCell(int producer, int consumer, long value){
        basePlan.get(producer).set(consumer, value);
        basicCellsCoordinates.add(new Pair(producer, consumer));
    }
}
