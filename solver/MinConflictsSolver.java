package solver;

import model.*;
import java.util.*;

// implements the min-conflicts local search heuristic for constraint satisfaction problems
public class MinConflictsSolver implements Solver {
    private final Random randomGen;
    private final int maximumSteps;

    // constructor to set random instance and maximum iteration steps
    public MinConflictsSolver(Random randomGen, int maximumSteps) {
        this.randomGen = randomGen;
        this.maximumSteps = maximumSteps;
    }

    @Override
    public String name() {
        return "Min-Conflicts";
    }

    @Override
    public SolveResult solve(Board startState) {
        long startTimeNanos = System.nanoTime();
        Board currentState = startState;
        int stepCount = 0;

        // loop until solution is found or step limit is reached
        while (stepCount < maximumSteps) {
            if (currentState.isSolved()) {
                return new SolveResult(true, stepCount,
                        System.nanoTime() - startTimeNanos, currentState);
            }

            int boardDimension = currentState.size();
            List<Integer> conflictedColumns = new ArrayList<>();

            // identify all columns containing queens currently involved in conflicts
            for (int colIndex = 0; colIndex < boardDimension; colIndex++) {
                if (currentState.conflictsForQueen(colIndex) > 0) {
                    conflictedColumns.add(colIndex);
                }
            }

            // break early if no conflicts remain
            if (conflictedColumns.isEmpty()) {
                break;
            }

            // randomly select one conflicted queen column
            int selectedCol = conflictedColumns.get(randomGen.nextInt(conflictedColumns.size()));

            int minimumConflictCost = Integer.MAX_VALUE;
            List<Integer> optimalRows = new ArrayList<>();

            // find the row placement in the selected column that minimizes total board conflicts
            for (int rowIndex = 0; rowIndex < boardDimension; rowIndex++) {
                Board candidateBoard = currentState.moveQueen(selectedCol, rowIndex);
                int candidateCost = candidateBoard.conflictPairs();
                if (candidateCost < minimumConflictCost) {
                    minimumConflictCost = candidateCost;
                    optimalRows.clear();
                    optimalRows.add(rowIndex);
                } else if (candidateCost == minimumConflictCost) {
                    optimalRows.add(rowIndex);
                }
            }

            // randomly choose among rows tied for the minimum conflict reduction
            int chosenRow = optimalRows.get(randomGen.nextInt(optimalRows.size()));
            currentState = currentState.moveQueen(selectedCol, chosenRow);
            stepCount++;
        }

        return new SolveResult(currentState.isSolved(), stepCount,
                System.nanoTime() - startTimeNanos, currentState);
    }
}