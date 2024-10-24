package gui

import repository.EWinner
import repository.Statistics

data class UiState(
    val board: List<List<Char>>,
    val winner: EWinner = EWinner.NONE,
    val stats: Statistics
)
