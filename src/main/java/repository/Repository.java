package repository;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Repository {
    private List<List<Character>> board;

    public Repository() {
        this.createBoard();
    }

    /**
     * Checks if the move is valid. If so it adds the move to the board, calls {@link #isValidMove(int, int) isValidMove}, finally calls {@link #computerMove(), computerMove}.
     */
    public void addPlayerMove(int row, int col) {
        if (isValidMove(row, col)) {
            board.get(row).set(col, ECharToken.PLAYER.token());

            if (isGameOver()) {
                return;
            }

            computerMove();
        }
    }

    /**
     * Resets all slots to the default empty character
     */
    public void resetBoard() {
        for (List<Character> row : board) {
            row.replaceAll(character -> ECharToken.EMPTY.token());
        }
    }

    /**
     * @return {@code true} if the current winner is not {@link EWinner#NONE NONE}
     */
    public boolean isGameOver() {
        return getWinner() != EWinner.NONE;
    }

    /**
     * @return {@code true} if the game isn't over and the selected spot is empty
     * else {@code false}.
     */
    public boolean isValidMove(int row, int col) {
        if (!isGameOver()) {
            return board.get(row).get(col) == ECharToken.EMPTY.token();
        }
        return false;
    }

    /**
     * @return {@code true} if it can not find an open spot
     */
    public boolean isBoardFull() {
        for (List<Character> row : board) {
            for (Character col : row) {
                if (col == ECharToken.EMPTY.token()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @return {@link EWinner}
     * <p>
     * {@link EWinner#PLAYER PLAYER} - When 3 player {@link ECharToken#PLAYER tokens} are found in a row
     * <p>
     * {@link EWinner#COMPUTER COMPUTER} - When 3 computer {@link ECharToken#COMPUTER tokens} are found in a row
     * <p>
     * {@link EWinner#TIE TIE} - When neither are found and the board is full
     * <p>
     * {@link EWinner#NONE NONE} - When neither are found and the board is not full
     */
    public EWinner getWinner() {
        boolean playerWon = false;
        boolean computerWon = false;

        StringBuilder rowStringBuilder = new StringBuilder();
        StringBuilder colStringBuilder = new StringBuilder();
        StringBuilder diagonalStringBuilder = new StringBuilder();

        // Check verticals and horizontals
        for (int row = 0; row < 3; row++) {
            rowStringBuilder.setLength(0);
            colStringBuilder.setLength(0);
            for (int col = 0; col < 3; col++) {
                rowStringBuilder.append(board.get(row).get(col));
                colStringBuilder.append(board.get(col).get(row));
            }

            String rowString = rowStringBuilder.toString();
            String colString = colStringBuilder.toString();

            if (rowString.equals("XXX") || colString.equals("XXX")) {
                playerWon = true;
            } else if (rowString.equals("OOO") || colString.equals("OOO")) {
                computerWon = true;
            }
        }

        // Check diagonals
        diagonalStringBuilder.append(board.get(1).get(1));

        diagonalStringBuilder.append(board.get(0).get(0));
        diagonalStringBuilder.append(board.get(2).get(2));

        String diagonalString = diagonalStringBuilder.toString();
        if (diagonalString.equals("XXX")) {
            playerWon = true;
        } else if (diagonalString.equals("OOO")) {
            computerWon = true;
        }

        diagonalStringBuilder.setLength(1);
        diagonalStringBuilder.append(board.get(0).get(2));
        diagonalStringBuilder.append(board.get(2).get(0));

        diagonalString = diagonalStringBuilder.toString();
        if (diagonalString.equals("XXX")) {
            playerWon = true;
        } else if (diagonalString.equals("OOO")) {
            computerWon = true;
        }

        if (playerWon) {
            return EWinner.PLAYER;
        } else if (computerWon) {
            return EWinner.COMPUTER;
        } else if (isBoardFull()) {
            return EWinner.TIE;
        } else {
            return EWinner.NONE;
        }
    }

    /**
     * @return an 2D unmodifiable list version of the board
     */
    public List<List<Character>> getBoard() {
        List<List<Character>> temp = new ArrayList<>();

        for (List<Character> row : board) {
            temp.add(Collections.unmodifiableList(row));
        }

        return Collections.unmodifiableList(temp);
    }

    public ImmutableList<ImmutableList<Character>> getImmutableBoard() {
        List<ImmutableList<Character>> temp = new ArrayList<>();

        for (List<Character> row : board) {
            temp.add(ImmutableList.copyOf(row));
        }

        return ImmutableList.copyOf(temp);
    }

    /**
     * Creates the empty 2D list that will stand as the base object for the board.
     * <p>
     * This should only be called once from the constructor. All views should only need to call `getBoard` once
     * meaning no more allocations after this function.
     */
    private void createBoard() {
        List<Character> emptyRow = new ArrayList<>(3);
        List<List<Character>> out = new ArrayList<>(3);

        for (int i = 0; i < 3; i++) {
            emptyRow.add(ECharToken.EMPTY.token());
        }

        for (int i = 0; i < 3; i++) {
            out.add(new ArrayList<>(emptyRow));
        }

        board = out;
    }

    /**
     * Executes the computer's move on the Tic-Tac-Toe board.
     * <p>
     * The computer follows a basic strategy:<br>
     * - If possible, take the center position.<br>
     * - If a move can help the computer win, make that move.<br>
     * - If a move can block the player from winning, make that move.<br>
     * - If no strategic move is possible, choose a random empty position.<br>
     * </p>
     * If the board is in an inconsistent state and no moves are available, the method will
     * throw a UnsupportedOperationException.
     *
     * @throws UnsupportedOperationException if no moves are available.
     */
    private void computerMove() {
        // Take center where can
        if (board.get(1).get(1) == ECharToken.EMPTY.token()) {
            board.get(1).set(1, ECharToken.COMPUTER.token());
            return;
        }

        int rowPos;
        int colPos;
        int[] pair;

        // Find an offensive move
        pair = findMove(ECharToken.COMPUTER.token(), ECharToken.PLAYER.token());
        rowPos = pair[0];
        colPos = pair[1];

        // If either is -1 then we couldn't find any "smart" moves.
        if (rowPos != -1 && colPos != -1) {
            board.get(rowPos).set(colPos, ECharToken.COMPUTER.token());
            return;
        }

        // Find a defensive move
        pair = findMove(ECharToken.PLAYER.token(), ECharToken.COMPUTER.token());
        rowPos = pair[0];
        colPos = pair[1];

        // If either is -1 then we couldn't find any "smart" moves.
        if (rowPos != -1 && colPos != -1) {
            board.get(rowPos).set(colPos, ECharToken.COMPUTER.token());
            return;
        }

        // This list will hold all empty board positions
        List<int[]> dumbMoves = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.get(row).get(col) == ECharToken.EMPTY.token()) {
                    dumbMoves.add(new int[]{row, col});
                }
            }
        }

        // computerMove shouldn't be called on a full board
        //  so the only reason we can't find a smart or dumb move is because
        //  something is wrong and our environment isn't known or predictable.
        if (dumbMoves.isEmpty()) {
            throw new UnsupportedOperationException("Could not find a smart or dumb move to make.\n" +
                    "Board: " + board);
        }

        // Randomly pick out a move from the list of dumbMoves
        Random random = new Random();
        pair = dumbMoves.get(random.nextInt(dumbMoves.size()));
        rowPos = pair[0];
        colPos = pair[1];

        board.get(rowPos).set(colPos, ECharToken.COMPUTER.token());
    }

    /**
     * Determines the best move for the computer in a Tic-Tac-Toe game.
     * This method analyzes the current state of the board and attempts to find
     * a strategic move to either block the player or take the game-winning move.
     *
     * @param playerToken   The {@link ECharToken token} representing the player (e.g., 'X' or 'O').
     * @param computerToken The {@link ECharToken token} representing the computer (e.g., 'X' or 'O').
     * @return An integer array with two elements, representing the row and column
     * indices of the best move for the computer. If no move is found, both
     * elements of the array will be -1.
     */
    @Contract("_, _ -> new")
    private int @NotNull [] findMove(Character playerToken, Character computerToken) {
        // Move finder works defensively by finding and preventing a three in a row.
        // playerToken is a parameter instead of hardcoding because if you give it the
        //  computerToken it will work offensively instead.

        StringBuilder rowStringBuilder = new StringBuilder();
        StringBuilder colStringBuilder = new StringBuilder();

        // Check for vertical and horizontal
        for (int row = 0; row < 3; row++) {
            // Reset the string builders
            rowStringBuilder.setLength(0);
            colStringBuilder.setLength(0);
            for (int col = 0; col < 3; col++) {
                rowStringBuilder.append(board.get(row).get(col));
                colStringBuilder.append(board.get(col).get(row));
            }

            // Get a chars stream and call
            //  and reduce it where each playerToken adds 1 and
            //  each computerToken subtracts 1.
            // This ensures that the count will be 2 only when there are 2 playerTokens and an
            //  empty space.
            long rowTokenCount = rowStringBuilder
                    .chars()
                    .reduce(0, (total, character) -> {
                        if (character == playerToken) {
                            return total + 1;
                        } else if (character == computerToken) {
                            return total - 1;
                        }
                        return total;
                    });

            long colTokenCount = colStringBuilder
                    .chars()
                    .reduce(0, (total, character) -> {
                        if (character == playerToken) {
                            return total + 1;
                        } else if (character == computerToken) {
                            return total - 1;
                        }
                        return total;
                    });

            // If the count is 2 then the smart move it to play a token in this line
            if (rowTokenCount == 2) {
                for (int col = 0; col < 3; col++) {
                    if (board.get(row).get(col) == ECharToken.EMPTY.token()) {
                        return new int[]{row, col};
                    }
                }

                // Somehow the count is 2 but there isn't a free spot
                throw new RuntimeException("Should be empty space in row " + row + " but none was found.\n" +
                        "Board: " + board);
            }

            if (colTokenCount == 2) {
                for (int col = 0; col < 3; col++) {
                    if (board.get(col).get(row) == ECharToken.EMPTY.token()) {
                        return new int[]{col, row};
                    }
                }
                throw new RuntimeException("Should be empty space in col " + row + " but none was found.\n" +
                        "Board: " + board);
            }
        }

        // Check diagonals
        StringBuilder diagonalStringBuilder = new StringBuilder();
        diagonalStringBuilder.append(board.get(1).get(1));

        diagonalStringBuilder.append(board.get(0).get(0));
        diagonalStringBuilder.append(board.get(2).get(2));

        long diagonalTokenCount = diagonalStringBuilder
                .chars()
                .reduce(0, (total, character) -> {
                    if (character == playerToken) {
                        return total + 1;
                    } else if (character == computerToken) {
                        return total - 1;
                    }
                    return total;
                });

        if (diagonalTokenCount == 2) {
            if (board.get(1).get(1) == ECharToken.EMPTY.token()) {
                return new int[]{1, 1};
            }

            if (board.get(0).get(0) == ECharToken.EMPTY.token()) {
                return new int[]{0, 0};
            }

            if (board.get(2).get(2) == ECharToken.EMPTY.token()) {
                return new int[]{2, 2};
            }
        }

        diagonalStringBuilder.setLength(1);

        diagonalStringBuilder.append(board.get(0).get(2));
        diagonalStringBuilder.append(board.get(2).get(0));

        diagonalTokenCount = diagonalStringBuilder
                .chars()
                .reduce(0, (total, character) -> {
                    if (character == playerToken) {
                        return total + 1;
                    } else if (character == computerToken) {
                        return total - 1;
                    }
                    return total;
                });

        if (diagonalTokenCount == 2) {
            if (board.get(1).get(1) == ECharToken.EMPTY.token()) {
                return new int[]{1, 1};
            }

            if (board.get(0).get(2) == ECharToken.EMPTY.token()) {
                return new int[]{0, 2};
            }

            if (board.get(2).get(0) == ECharToken.EMPTY.token()) {
                return new int[]{2, 0};
            }
        }

        return new int[]{-1, -1};
    }
}
