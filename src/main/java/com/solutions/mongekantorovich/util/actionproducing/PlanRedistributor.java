package com.solutions.mongekantorovich.util.actionproducing;

import com.solutions.mongekantorovich.util.containers.Pair;

import java.util.*;

public class PlanRedistributor {
    private boolean isFound;

    public List<List<Long>> redistribute(
            Pair minCell,
            List<List<Long>> plan,
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {
        List<List<Long>> newPlan = new ArrayList<>(plan);

        addCell(
                minCell,
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        // TODO: there must be space for improvement
        List<Pair> cycle = new LinkedList<>();
        cycle.add(new Pair(minCell.producer(), minCell.consumer()));

        dfsCycle();

        return newPlan;
    }

    private void addCell(
            Pair minCell,
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {
        basicCells.add(minCell);
        basicCellsProducerConsumers.get(minCell.producer()).add(minCell.consumer());
        basicCellsConsumerProducers.get(minCell.consumer()).add(minCell.producer());
    }

    private void dfsCycle() {
    }
}
