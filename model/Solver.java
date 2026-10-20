package model;

// interface defining the core behavior for n-queens local search solvers
public interface Solver {
    
    // execute the search algorithm starting from a given board state
    SolveResult solve(Board startState);
    
    // return the display name of the algorithm implementation
    String name();
}