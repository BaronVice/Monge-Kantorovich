package com.solutions.mongekantorovich.util.actionproducing;

import com.solutions.mongekantorovich.util.containers.Pair;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicCellsMapBuilder {
    public static void buildMaps(
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
}
