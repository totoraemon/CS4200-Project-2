import model.*;
import solver.*;
import java.util.*;

// main execution driver for CS4200 Project 2 evaluating n-queens local search algorithms
public class Main {
    public static void main(String[] args) {
        // define problem sizes required by project specifications (n = 8, 12, 16)
        int[] boardSizes = {8, 12, 16};
        int trialCount = 100; // number of test instances per n as specified in rubric

        // initialize solvers with appropriate step thresholds
        Solver hillClimbingSolver = new HillClimbingSolver(new Random(), 1000);
        Solver minConflictsSolver = new MinConflictsSolver(new Random(), 5000);

        List<Solver> activeSolvers = List.of(hillClimbingSolver, minConflictsSolver);

        System.out.println("CS4200 Project 2 - N-Queens Local Search Evaluation\n");
        System.out.println("Tested Board Sizes: " + Arrays.toString(boardSizes));
        System.out.println("Trials per Board Size: " + trialCount + "\n");
        System.out.println("==================================================");

        Random experimentRandom = new Random();

        // iterate through each required board size n
        for (int boardSize : boardSizes) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("Evaluating for N = " + boardSize);
            System.out.println("--------------------------------------------------");

            // run trials for each algorithm
            for (Solver currentSolver : activeSolvers) {
                int solvedCount = 0;
                long cumulativeSteps = 0;
                long cumulativeTimeNanos = 0;
                List<Board> solutionSamples = new ArrayList<>();

                for (int trialIndex = 0; trialIndex < trialCount; trialIndex++) {
                    Board initialBoard = Board.random(boardSize, experimentRandom);
                    SolveResult result = currentSolver.solve(initialBoard);

                    if (result.isSuccessful) {
                        solvedCount++;
                        cumulativeSteps += result.stepCount;
                        cumulativeTimeNanos += result.executionTimeNanos;

                        // collect up to 3 sample solutions as required
                        if (solutionSamples.size() < 3) {
                            solutionSamples.add(result.finalBoardState);
                        }
                    }
                }

                // display performance metrics for the current algorithm and n
                System.out.println("\nAlgorithm: " + currentSolver.name());
                System.out.printf("Solved Successfully: %d / %d (%.1f%%)%n",
                        solvedCount, trialCount, (100.0 * solvedCount / trialCount));

                if (solvedCount > 0) {
                    System.out.printf("Average Search Steps: %.2f%n",
                            cumulativeSteps * 1.0 / solvedCount);
                    System.out.printf("Average Execution Time: %.4f ms%n",
                            cumulativeTimeNanos / 1_000_000.0 / solvedCount);
                } else {
                    System.out.println("No solutions found within test parameters.");
                }

                // print sample solutions
                System.out.println("Sample Solutions:");
                if (solutionSamples.isEmpty()) {
                    System.out.println("  None available.");
                } else {
                    for (int sampleIndex = 0; sampleIndex < solutionSamples.size(); sampleIndex++) {
                        System.out.println("  Solution " + (sampleIndex + 1) + ": " + solutionSamples.get(sampleIndex));
                    }
                }
                System.out.println();
            }
        }
        System.out.println("==================================================");
        System.out.println("Execution completed successfully.");
    }
}