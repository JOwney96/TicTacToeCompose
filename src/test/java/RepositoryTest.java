import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ECharToken;
import repository.EWinner;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {
    @Test
    @DisplayName("Can create empty repo")
    void createRepo() {
        Repository repository = new Repository();
        List<List<Character>> board = repository.getBoard();

        for (List<Character> row : board) {
            for (Character col : row) {
                if (col != ECharToken.EMPTY.token()) {
                    fail();
                }
            }
        }
    }

    @Test
    @DisplayName("Can add player move")
    void playerMove() {
        Repository repository = new Repository();

        repository.addPlayerMove(1, 1);

        assertEquals(ECharToken.PLAYER.token(), repository.getBoard().get(1).get(1));
    }

    @Test
    @DisplayName("Board is an unmodifiable list")
    void unmodifiableView() {
        Repository repository = new Repository();
        List<List<Character>> board = repository.getBoard();

        repository.addPlayerMove(1, 1);

        assertEquals(ECharToken.PLAYER.token(), board.get(1).get(1));
        assertThrows(UnsupportedOperationException.class, () -> board.add(new ArrayList<>()));
        assertThrows(UnsupportedOperationException.class, () -> board.get(1).set(1, ECharToken.PLAYER.token()));
    }

    @Test
    @DisplayName("Board is an immutable list")
    void immutableView() {
        Repository repository = new Repository();
        ImmutableList<ImmutableList<Character>> board = repository.getImmutableBoard();

        repository.addPlayerMove(1, 1);

        assertEquals(ECharToken.EMPTY.token(), board.get(1).get(1));
        assertThrows(UnsupportedOperationException.class, () -> board.get(1).set(1, ECharToken.PLAYER.token()));
    }

    @Test
    @DisplayName("Can reset board")
    void resetBoard() {
        Repository repository = new Repository();
        List<List<Character>> board = repository.getBoard();

        repository.addPlayerMove(1, 1);
        assertEquals(ECharToken.PLAYER.token(), board.get(1).get(1));

        repository.resetBoard();
        assertEquals(ECharToken.EMPTY.token(), board.get(1).get(1));
    }

    @Test
    @DisplayName("Computer plays a move")
    void computerMove() {
        Repository repository = new Repository();
        List<List<Character>> board = repository.getBoard();

        repository.addPlayerMove(1, 1);
        assertEquals(ECharToken.PLAYER.token(), board.get(1).get(1));

        boolean foundComputerMove = false;

        for (List<Character> row : board) {
            for (Character col : row) {
                if (col == ECharToken.COMPUTER.token()) {
                    foundComputerMove = true;
                    break;
                }
            }
        }

        assertTrue(foundComputerMove);
    }

    @Test
    @DisplayName("Can play game with AI")
    void playGame() {
        Repository repository = new Repository();
        List<List<Character>> board = repository.getBoard();

        while (!repository.isGameOver()) {
            int[] pair = getRandomMove(board);
            int row = pair[0];
            int col = pair[1];

            repository.addPlayerMove(row, col);
        }

        EWinner winner = repository.getWinner();

        assertNotSame(winner, EWinner.NONE);
    }

    @Test
    @DisplayName("Can play multiple games with AI")
    void playMultipleGame() {
        Repository repository = new Repository();
        List<List<Character>> board = repository.getBoard();

        for (int i = 0; i < 5; i++) {
            while (!repository.isGameOver()) {
                int[] pair = getRandomMove(board);
                int row = pair[0];
                int col = pair[1];

                repository.addPlayerMove(row, col);
            }

            EWinner winner = repository.getWinner();
            assertNotSame(winner, EWinner.NONE);

            repository.resetBoard();
        }
    }

    @Test
    @DisplayName("Can play game with AI with stats")
    void playGameWithStats() {
        Repository repository = new Repository();
        List<List<Character>> board = repository.getBoard();
        repository.resetStats();

        assertEquals(repository.getStats().wins, 0);
        assertEquals(repository.getStats().loses, 0);
        assertEquals(repository.getStats().ties, 0);

        while (!repository.isGameOver()) {
            int[] pair = getRandomMove(board);
            int row = pair[0];
            int col = pair[1];

            repository.addPlayerMove(row, col);
        }

        EWinner winner = repository.getWinner();

        if (winner == EWinner.PLAYER) {
            assertEquals(repository.getStats().wins, 1);
        } else if (winner == EWinner.COMPUTER) {
            assertEquals(repository.getStats().loses, 1);
        } else {
            assertEquals(repository.getStats().ties, 1);
        }

        assertNotSame(winner, EWinner.NONE);

        repository.resetStats();
    }

    private int[] getRandomMove(List<List<Character>> board) {
        List<int[]> moves = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.get(row).get(col) == ECharToken.EMPTY.token()) {
                    moves.add(new int[]{row, col});
                }
            }
        }

        Random random = new Random();
        return moves.get(random.nextInt(moves.size()));
    }
}
