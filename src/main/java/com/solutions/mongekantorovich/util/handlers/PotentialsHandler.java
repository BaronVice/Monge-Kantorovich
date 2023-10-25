package com.solutions.mongekantorovich.util.handlers;

import com.solutions.mongekantorovich.util.actionproducing.BasicCellsArrayBuilder;
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
        List<Set<Integer>> basicCellsProducerConsumers = new ArrayList<>();
        List<Set<Integer>> basicCellsConsumerProducers = new ArrayList<>();

        BasicCellsArrayBuilder.buildArrays(
                basicCells,
                plan.size(),
                plan.get(0).size(),
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
            List<Set<Integer>> basicCellsProducerConsumers,
            List<Set<Integer>> basicCellsConsumerProducers
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
    }

    private static void dfsPotentials(
            int potentialPosition,
            boolean isPotentialProducer,
            List<Set<Integer>> basicCellsProducerConsumers,
            List<Set<Integer>> basicCellsConsumerProducers,
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
            List<Set<Integer>> basicCellsProducerConsumers,
            List<Set<Integer>> basicCellsConsumerProducers,
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
            List<Set<Integer>> basicCellsProducerConsumers,
            List<Set<Integer>> basicCellsConsumerProducers,
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
