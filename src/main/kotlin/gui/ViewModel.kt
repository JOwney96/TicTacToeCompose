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

        uiState = UiState(repo.immutableBoard)
    }
}