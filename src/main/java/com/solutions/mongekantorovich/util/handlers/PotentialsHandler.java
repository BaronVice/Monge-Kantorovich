package com.solutions.mongekantorovich.util.handlers;

import com.solutions.mongekantorovich.util.Pair;
import com.solutions.mongekantorovich.util.PotentialsSolution;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PotentialsHandler {
    public static List<PotentialsSolution> calculatePotentials(
            List<List<Long>> costs,
            List<List<Long>> plan,
            List<Pair> basicCells
    ){
        List<PotentialsSolution> potentialsSolutions = new ArrayList<>();
        Map<Integer, List<Integer>> basicCellsProducerConsumers = new LinkedHashMap<>();
        Map<Integer, List<Integer>> basicCellsConsumerProducers = new LinkedHashMap<>();

        buildMaps(
                basicCells,
                basicCellsProducerConsumers,
                basicCellsConsumerProducers
        );

        // TODO: what args tho (put maps)?
        buildSolution(
                potentialsSolutions,
                costs,
                plan,
                basicCells
        );

        return potentialsSolutions;
    }

    private static void buildMaps(
            List<Pair> basicCells,
            Map<Integer, List<Integer>> basicCellsProducerConsumers,
            Map<Integer, List<Integer>> basicCellsConsumerProducers
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
            Map<Integer, List<Integer>> map
    ){
        if (map.containsKey(key))
            map.get(key).add(value);
        else
            map.put(key, new ArrayList<>(List.of(value)));
    }

    private static void buildSolution(
            List<PotentialsSolution> potentialsSolutions,
            List<List<Long>> costs,
            List<List<Long>> plan,
            List<Pair> basicCells
    ){
        PotentialsSolution solution = new PotentialsSolution(plan);
        solution.getU().set(0, 0L);
    }
}
