package model;

import java.util.*;

// represents an n-queens board configuration using a single-dimensional array
// where each index represents a column and the stored value represents the row
public class Board {
    private final int boardSize;
    private final int[] queenPositions;

    // constructor to initialize board with size and queen positions
    public Board(int boardSize, int[] queenPositions) {
        this.boardSize = boardSize;
        this.queenPositions = Arrays.copyOf(queenPositions, queenPositions.length);
    }

    // factory method to generate a random board configuration for size n
    public static Board random(int boardSize, Random randomGen) {
        int[] positions = new int[boardSize];
        for (int colIndex = 0; colIndex < boardSize; colIndex++) {
            positions[colIndex] = randomGen.nextInt(boardSize);
        }
        return new Board(boardSize, positions);
    }

    // return the dimension size of the board
    public int size() {
        return boardSize;
    }

    // get the row index of the queen stationed in a specific column
    public int rowAt(int colIndex) {
        return queenPositions[colIndex];
    }

    // return a new board instance with a queen moved to a new row in the specified column
    public Board moveQueen(int colIndex, int targetRow) {
        int[] nextPositions = Arrays.copyOf(queenPositions, queenPositions.length);
        nextPositions[colIndex] = targetRow;
        return new Board(boardSize, nextPositions);
    }

    // calculate the total number of conflicting queen pairs on the board
    public int conflictPairs() {
        int totalConflicts = 0;
        for (int firstCol = 0; firstCol < boardSize; firstCol++) {
            for (int secondCol = firstCol + 1; secondCol < boardSize; secondCol++) {
                int row1 = queenPositions[firstCol];
                int row2 = queenPositions[secondCol];

                // check for same row conflicts
                if (row1 == row2) {
                    totalConflicts++;
                }
                // check for diagonal conflicts
                if (Math.abs(firstCol - secondCol) == Math.abs(row1 - row2)) {
                    totalConflicts++;
                }
            }
        }
        return totalConflicts;
    }

    // check if the current board state has zero conflicts (is solved)
    public boolean isSolved() {
        return conflictPairs() == 0;
    }

    // calculate the number of conflicts involving the queen in a specific column
    public int conflictsForQueen(int colIndex) {
        int queenConflicts = 0;
        int targetRow = queenPositions[colIndex];
        for (int otherCol = 0; otherCol < boardSize; otherCol++) {
            if (otherCol == colIndex) {
                continue;
            }
            int otherRow = queenPositions[otherCol];
            // check row collision
            if (targetRow == otherRow) {
                queenConflicts++;
            }
            // check diagonal collision
            if (Math.abs(colIndex - otherCol) == Math.abs(targetRow - otherRow)) {
                queenConflicts++;
            }
        }
        return queenConflicts;
    }

    @Override
    public String toString() {
        return Arrays.toString(queenPositions);
    }
}