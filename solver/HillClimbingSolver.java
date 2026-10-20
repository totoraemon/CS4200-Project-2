package solver;

import model.*;
import java.util.*;

// implements steepest-ascent hill climbing local search for the n-queens problem
public class HillClimbingSolver implements Solver {
    private final Random randomGen;
    private final int maximumSteps;

    // constructor to initialize random generator and step limit
    public HillClimbingSolver(Random randomGen, int maximumSteps) {
        this.randomGen = randomGen;
        this.maximumSteps = maximumSteps;
    }

    @Override
    public String name() {
        return "Steepest-Ascent Hill Climbing";
    }

    @Override
    public SolveResult solve(Board startState) {
        long startTimeNanos = System.nanoTime();
        Board currentState = startState;
        int stepCount = 0;

        // main search loop up to the maximum allowed steps
        while (stepCount < maximumSteps) {
            int currentCost = currentState.conflictPairs();
            if (currentCost == 0) {
                return new SolveResult(true, stepCount,
                        System.nanoTime() - startTimeNanos, currentState);
            }

            Board bestNeighborState = currentState;
            int bestNeighborCost = currentCost;
            int boardDimension = currentState.size();

            // evaluate all possible single-queen moves to find the steepest ascent neighbor
            for (int colIndex = 0; colIndex < boardDimension; colIndex++) {
                for (int rowIndex = 0; rowIndex < boardDimension; rowIndex++) {
                    if (rowIndex == currentState.rowAt(colIndex)) {
                        continue;
                    }
                    Board candidateNeighbor = currentState.moveQueen(colIndex, rowIndex);
                    int candidateCost = candidateNeighbor.conflictPairs();
                    if (candidateCost < bestNeighborCost) {
                        bestNeighborCost = candidateCost;
                        bestNeighborState = candidateNeighbor;
                    }
                }
            }

            // terminate if stuck in a local minimum or plateau where no neighbor improves the cost
            if (bestNeighborCost >= currentCost) {
                return new SolveResult(false, stepCount,
                        System.nanoTime() - startTimeNanos, currentState);
            }

            currentState = bestNeighborState;
            stepCount++;
        }

        return new SolveResult(currentState.isSolved(), stepCount,
                System.nanoTime() - startTimeNanos, currentState);
    }
}