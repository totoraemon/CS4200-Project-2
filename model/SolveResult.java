package model;

// container class holding the outcome metrics of a single solver run
public class SolveResult {
    public final boolean isSuccessful;
    public final int stepCount;
    public final long executionTimeNanos;
    public final Board finalBoardState;

    // constructor to initialize all result fields
    public SolveResult(boolean isSuccessful, int stepCount, long executionTimeNanos, Board finalBoardState) {
        this.isSuccessful = isSuccessful;
        this.stepCount = stepCount;
        this.executionTimeNanos = executionTimeNanos;
        this.finalBoardState = finalBoardState;
    }
}