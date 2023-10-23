package com.solutions.mongekantorovich.util.handlers;

import com.solutions.mongekantorovich.util.actionproducing.BasicCellsMapBuilder;
import com.solutions.mongekantorovich.util.actionproducing.PlanRedistributor;
import com.solutions.mongekantorovich.util.containers.Pair;
import com.solutions.mongekantorovich.util.containers.PotentialsSolution;

import java.util.*;

public class PotentialsHandler {
    public static List<PotentialsSolution> calculatePotentials(
            List<List<Long>> costs,
            List<List<Long>> plan,
            List<Pair> basicCells
    ){
        List<PotentialsSolution> potentialsSolutions = new ArrayList<>();
        // TODO: I assume it can be replaced to List<List<Integer>>
        //  (same memory usage as it's guaranteed every row and column has a cell, but better performance)
        Map<Integer, Set<Integer>> basicCellsProducerConsumers = new LinkedHashMap<>();
        Map<Integer, Set<Integer>> basicCellsConsumerProducers = new LinkedHashMap<>();

        BasicCellsMapBuilder.buildMaps(
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

    private static void buildSolution(
            List<PotentialsSolution> potentialsSolutions,
            List<List<Long>> costs,
            List<List<Long>> plan,
            List<Pair> basicCells,
            Map<Integer, Set<Integer>> basicCellsProducerConsumers,
            Map<Integer, Set<Integer>> basicCellsConsumerProducers
    ){
        PotentialsSolution solution = new PotentialsSolution(plan, costs, basicCells);
        potentialsSolutions.add(solution);

        solution.getU().set(0, 0L);

        dfsPotentials(
                0,
                true,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers,
                solution,
                costs
        );

        Pair minCell = solution.applyPotentialsOnCosts();
        if (minCell.consumer() == -1)
            return;

        PlanRedistributor redistributor = new PlanRedistributor();
        List<List<Long>> newPlan = redistributor.redistribute(
                minCell,
                plan,
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        buildSolution(
                potentialsSolutions,
                costs,
                newPlan,
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        // TODO: otherwise rebuild plan (and so basicCells will be changed as well as maps)
        //  and call it again
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
