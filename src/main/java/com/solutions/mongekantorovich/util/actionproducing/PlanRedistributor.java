package com.solutions.mongekantorovich.util.actionproducing;

import com.solutions.mongekantorovich.util.containers.Pair;

import java.util.*;

public class PlanRedistributor {
    private boolean isFound;
    private List<Pair> cycle;

    public PlanRedistributor() {
        cycle = new ArrayList<>();
    }

    public List<List<Long>> redistribute(
            Pair minCell,
            List<List<Long>> plan,
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {

        findCycle(
                minCell,
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        return buildNewPlan(
                plan,
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );
    }

    private List<List<Long>> buildNewPlan(
            List<List<Long>> plan,
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {
        List<List<Long>> newPlan = new ArrayList<>(plan);

        long minValue = Long.MAX_VALUE;
        Pair toRemove = null;
        for (int i = 1; i < cycle.size(); i+=2) {
            Pair cell = cycle.get(i);
            long value = newPlan.get(cell.producer()).get(cell.consumer());

            if (minValue > newPlan.get(cell.producer()).get(cell.consumer())){
                minValue = value;
                toRemove = cell;
            }
        }
        for (int i = 0; i < cycle.size(); i++){
            Pair cell = cycle.get(i);
            long value = newPlan.get(cell.producer()).get(cell.consumer());

            if (i % 2 == 0){
                value += minValue;
            } else {
                value -= minValue;
            }

            newPlan.get(cell.producer()).set(cell.consumer(), value);
        }

        redistributeBasicCells(
                newPlan,
                toRemove,
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        return newPlan;
    }

    private void redistributeBasicCells(
            List<List<Long>> newPlan,
            Pair toRemove,
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {
        Pair minCell = cycle.get(0);
        long minCellValue = newPlan.get(minCell.producer()).get(minCell.consumer()) + 1;

        newPlan.get(minCell.producer()).set(minCell.consumer(), minCellValue);
        newPlan.get(toRemove.producer()).set(toRemove.consumer(), -1L);

        basicCells.remove(toRemove);
        basicCellsProducerConsumers.get(toRemove.producer()).remove(toRemove.consumer());
        basicCellsConsumerProducers.get(toRemove.consumer()).remove(toRemove.producer());
    }

    private void findCycle(
            Pair minCell,
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ){
        addCell(
                minCell,
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );
        cycle.add(new Pair(minCell.producer(), minCell.consumer()));

        launchDfs(
                minCell,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        if (cycle.size() % 2 != 0)
            throw new RuntimeException("Odd size of cycle (can't handle it now)");
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

    private void launchDfs(
            Pair minCell,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {
        for (int consumer : basicCellsProducerConsumers.get(minCell.producer())) if (consumer != minCell.consumer()){
            dfsCycle(
                    minCell.producer(),
                    consumer,
                    false,
                    basicCellsProducerConsumers,
                    basicCellsConsumerProducers
            );
        }
        for (int producer : basicCellsConsumerProducers.get(minCell.consumer())) if (producer != minCell.producer()){
            dfsCycle(
                    producer,
                    minCell.consumer(),
                    true,
                    basicCellsProducerConsumers,
                    basicCellsConsumerProducers
            );
        }
    }

    private void dfsCycle(
            int visitedProducer,
            int visitedConsumer,
            boolean checkProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {
        if (visitedProducer == cycle.get(0).producer() && visitedConsumer == cycle.get(0).consumer())
            isFound = true;

        if (isFound)
            return;

        cycle.add(new Pair(visitedProducer, visitedConsumer));

        if (checkProducerConsumers) {
            dfsCycleProducersConsumers(
                    visitedProducer,
                    visitedConsumer,
                    basicCellsProducerConsumers,
                    basicCellsConsumerProducers
            );
        } else {
            dfsCycleConsumerProducers(
                    visitedProducer,
                    visitedConsumer,
                    basicCellsProducerConsumers,
                    basicCellsConsumerProducers
            );
        }

        if (!isFound)
            cycle.remove(cycle.size() - 1);

    }

    private void dfsCycleProducersConsumers(
            int visitedProducer,
            int visitedConsumer,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {
        for (int consumer : basicCellsProducerConsumers.get(visitedProducer))
            if (consumer != visitedConsumer) {
                dfsCycle(
                        visitedProducer,
                        consumer,
                        false,
                        basicCellsProducerConsumers,
                        basicCellsConsumerProducers
                );
            }
    }

    private void dfsCycleConsumerProducers(
            int visitedProducer,
            int visitedConsumer,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ) {
        for (int producer : basicCellsConsumerProducers.get(visitedConsumer))
            if (producer != visitedProducer) {
                dfsCycle(
                        producer,
                        visitedConsumer,
                        true,
                        basicCellsProducerConsumers,
                        basicCellsConsumerProducers
                );
            }
    }
}
