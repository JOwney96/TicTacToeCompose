package gui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import repository.Repository

class ViewModel(private val repo: Repository) {
    var uiState by mutableStateOf(UiState(repo.immutableBoard))
        private set

    fun gameButtonClickHandler(row: Int, col: Int) {
        repo.addPlayerMove(row, col)

        uiState = uiState.copy(board = repo.immutableBoard, winner = repo.winner)
    }

    fun newGameClickHandler() {
        repo.resetBoard()

        uiState = uiState.copy(board = repo.immutableBoard, winner = repo.winner)
    }
}