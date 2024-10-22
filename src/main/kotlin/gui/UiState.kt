package gui

import repository.EWinner

data class UiState(
    val board: List<List<Char>>,
    val winner: EWinner = EWinner.NONE
)
