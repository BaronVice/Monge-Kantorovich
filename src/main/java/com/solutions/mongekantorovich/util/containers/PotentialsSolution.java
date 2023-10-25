package com.solutions.mongekantorovich.util.containers;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
public class PotentialsSolution {
    private List<Long> u;
    private List<Long> v;
    private List<List<Long>> plan;
    private List<List<Long>> tablePotentials;
    private long cost;

    public PotentialsSolution(
            List<List<Long>> plan,
            List<List<Long>> costs,
            List<Pair> basicCells
    ){
        this.plan = plan;

        u = new ArrayList<>(Collections.nCopies(plan.size(), null));
        v = new ArrayList<>(Collections.nCopies(plan.get(0).size(), null));
        fillTable(costs);
        calculateCost(basicCells, costs);
    }

    private void fillTable(List<List<Long>> costs){
        this.tablePotentials = new ArrayList<>();
        for (int i = 0; i < plan.size(); i++) {
            this.tablePotentials.add(
                    new ArrayList<>(costs.get(i))
            );
        }
    }

    private void calculateCost(
            List<Pair> basicCells,
            List<List<Long>> costs
    ){
        cost = 0;
        for (Pair cell : basicCells) {
            cost += plan.get(cell.producer()).get(cell.consumer()) * costs.get(cell.producer()).get(cell.consumer());
        }
    }

    public Pair applyPotentialsOnCosts() {
        long minValue = 0;
        int producer = -1, consumer = -1;

        for (int i = 0; i < u.size(); i++) {
            for (int j = 0; j < v.size(); j++) {
                long value = tablePotentials.get(i).get(j) - u.get(i) - v.get(j);
                tablePotentials.get(i).set(j, value);

                if (value < minValue){
                    minValue = value;
                    producer = i;
                    consumer = j;
                }
            }
        }

        return new Pair(producer, consumer);
    }
}
