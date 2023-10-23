package com.solutions.mongekantorovich.util.handlers;

import com.solutions.mongekantorovich.util.Pair;
import com.solutions.mongekantorovich.util.PotentialsSolution;

import java.util.*;

public class PotentialsHandler {
    public static List<PotentialsSolution> calculatePotentials(
            List<List<Long>> costs,
            List<List<Long>> plan,
            List<Pair> basicCells
    ){
        List<PotentialsSolution> potentialsSolutions = new ArrayList<>();
        Map<Integer, Set<Integer>> basicCellsProducerConsumers = new LinkedHashMap<>();
        Map<Integer, Set<Integer>> basicCellsConsumerProducers = new LinkedHashMap<>();

        buildMaps(
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        buildSolution(
                potentialsSolutions,
                costs,
                plan,
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        return potentialsSolutions;
    }

    private static void buildMaps(
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ){
        for (Pair cell : basicCells) {
            handleAdditionInMap(
                    cell.producer(),
                    cell.consumer(),
                    basicCellsProducerConsumers
            );
            handleAdditionInMap(
                    cell.consumer(),
                    cell.producer(),
                    basicCellsConsumerProducers
            );
        }
    }

    private static void handleAdditionInMap(
            Integer key,
            Integer value,
            Map<Integer, Set<Integer>> map
    ){
        if (map.containsKey(key))
            map.get(key).add(value);
        else
            map.put(key, new LinkedHashSet<>(Set.of(value)));
    }

    private static void buildSolution(
            List<PotentialsSolution> potentialsSolutions,
            List<List<Long>> costs,
            List<List<Long>> plan,
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ){
        PotentialsSolution solution = new PotentialsSolution(plan);
        solution.getU().set(0, 0L);

        dfsPotentials(
                0,
                true,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers,
                solution,
                costs
        );

        potentialsSolutions.add(solution);
    }

    private static void dfsPotentials(
            int potentialPosition,
            boolean isPotentialProducer,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers,
            PotentialsSolution solution,
            List<List<Long>> costs
    ) {
        if (isPotentialProducer){
            dfsPotentialProducer(
                    potentialPosition,
                    basicCellsProducerConsumers,
                    basicCellsConsumerProducers,
                    solution,
                    costs
            );
        } else {
            dfsPotentialConsumer(
                    potentialPosition,
                    basicCellsProducerConsumers,
                    basicCellsConsumerProducers,
                    solution,
                    costs
            );
        }
    }

    private static void dfsPotentialProducer(
            int potentialPosition,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers,
            PotentialsSolution solution,
            List<List<Long>> costs
    ){
        for (int consumer : basicCellsProducerConsumers.get(potentialPosition)){
            if (solution.getV().get(consumer) == null){
                long value = costs.get(potentialPosition).get(consumer) - solution.getU().get(potentialPosition);
                solution.getV().set(consumer, value);

                dfsPotentials(
                        consumer,
                        false,
                        basicCellsProducerConsumers,
                        basicCellsConsumerProducers,
                        solution,
                        costs
                );
            }
        }
    }

    private static void dfsPotentialConsumer(
            int potentialPosition,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers,
            PotentialsSolution solution,
            List<List<Long>> costs
    ){
        for (int producer : basicCellsConsumerProducers.get(potentialPosition)){
            if (solution.getU().get(producer) == null){
                long value = costs.get(producer).get(potentialPosition) - solution.getV().get(potentialPosition);
                solution.getU().set(producer, value);

                dfsPotentials(
                        producer,
                        true,
                        basicCellsProducerConsumers,
                        basicCellsConsumerProducers,
                        solution,
                        costs
                );
            }
        }
    }
}
