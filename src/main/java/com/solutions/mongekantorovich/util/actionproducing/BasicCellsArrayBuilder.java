package com.solutions.mongekantorovich.util.actionproducing;

import com.solutions.mongekantorovich.util.containers.Pair;

import java.util.*;

public class BasicCellsArrayBuilder {
    public static void buildArrays(
            List<Pair> basicCells,
            int producers,
            int consumers,
            List<Set<Integer>> basicCellsProducerConsumers,
            List<Set<Integer>> basicCellsConsumerProducers
    ){
        init(producers, basicCellsProducerConsumers);
        init(consumers, basicCellsConsumerProducers);

        for (Pair cell : basicCells) {
            basicCellsProducerConsumers.get(cell.producer()).add(cell.consumer());
            basicCellsConsumerProducers.get(cell.consumer()).add(cell.producer());
        }
    }

    private static void init(
            int size,
            List<Set<Integer>> list
    ) {
        for (int i = 0; i < size; i++) {
            list.add(new LinkedHashSet<>());
        }
    }
}
