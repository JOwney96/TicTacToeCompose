package gui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import repository.EWinner
import repository.Repository

/**
 * ViewModel class responsible for managing the UI state and handling user interactions with the game.
 *
 * @property repo The repository instance which holds the game logic and data.
 */
class ViewModel(private val repo: Repository) {
    var uiState by mutableStateOf(UiState(board = repo.immutableBoard, stats = repo.stats))
        private set

    /**
     * Handles the button click events in the game board.
     *
     * This function adds a player's move to the board, updates the UI state,
     * and checks if the game has a winner. If a winner is found, it also updates
     * the game's statistics.
     *
     * @param row The row index where the player has made their move.
     * @param col The column index where the player has made their move.
     */
    fun gameButtonClickHandler(row: Int, col: Int) {
        repo.addPlayerMove(row, col)

        uiState = uiState.copy(board = repo.immutableBoard, winner = repo.winner)

        if (uiState.winner != EWinner.NONE) {
            uiState = uiState.copy(stats = repo.stats)
        }
    }

    /**
     * Handles the click event for starting a new game.
     *
     * This method resets the game board to its initial state and updates the UI state to reflect the reset board
     * and no winner. The method interacts with the repository to reset the game logic.
     */
    fun newGameClickHandler() {
        repo.resetBoard()

        uiState = uiState.copy(board = repo.immutableBoard, winner = repo.winner)
    }

    /**
     * Handles the click event for resetting game statistics.
     *
     * This function interacts with the repository to reset the game statistics
     * including wins, loses, and ties. It updates the UI state to reflect the
     * reset statistics.
     *
     * @throws RuntimeException if the statistics file exists but cannot be deleted.
     */
    fun resetStatsClickHandler() {
        repo.resetStats()

        uiState = uiState.copy(stats = repo.stats)
    }
}