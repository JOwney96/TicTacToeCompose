package gui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import repository.EWinner
import repository.Repository

class ViewModel(private val repo: Repository) {
    var uiState by mutableStateOf(UiState(board = repo.immutableBoard, stats = repo.stats))
        private set

    fun gameButtonClickHandler(row: Int, col: Int) {
        repo.addPlayerMove(row, col)

        uiState = uiState.copy(board = repo.immutableBoard, winner = repo.winner)

        if (uiState.winner != EWinner.NONE) {
            uiState = uiState.copy(stats = repo.stats)
        }
    }

    fun newGameClickHandler() {
        repo.resetBoard()

        uiState = uiState.copy(board = repo.immutableBoard, winner = repo.winner)
    }

    fun resetStatsClickHandler() {
        repo.resetStats()
        
        uiState = uiState.copy(stats = repo.stats)
    }
}