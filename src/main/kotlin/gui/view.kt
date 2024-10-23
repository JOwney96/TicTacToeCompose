package gui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import gui.theme.AppTheme
import repository.EWinner

@Composable
fun MainUi(viewModel: ViewModel) {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Board(viewModel)
            NewGameButton(viewModel)
            WinnerText(viewModel)
        }
    }
}

@Composable
fun WinnerText(viewModel: ViewModel) {
    val text: String
    if (viewModel.uiState.winner != EWinner.NONE) {
        text = when (viewModel.uiState.winner) {
            EWinner.PLAYER -> "Player Won"
            EWinner.COMPUTER -> "Computer Won"
            EWinner.TIE -> "Tie"
            EWinner.NONE -> "No winner yet"
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.border(
                    width = 1.dp, color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(3.dp)
                )
            ) {
                Text(
                    text,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.zIndex(1.0f).padding(top = 1.dp, bottom = 1.dp, start = 5.dp, end = 5.dp)
                )
            }
        }
    }
}

@Composable
fun NewGameButton(viewModel: ViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { viewModel.newGameClickHandler() }) {
            Text("New Game")
        }
    }
}

@Composable
fun Board(viewModel: ViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier /*.fillMaxWidth()*/
            .padding(25.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(2.dp)
            ),
    ) {
        Row {
            for (i in 0..2) {
                GameButton(viewModel.uiState.board[0][i].toString()) { viewModel.gameButtonClickHandler(0, i) }
            }
        }
        Row {
            for (i in 0..2) {
                GameButton(viewModel.uiState.board[1][i].toString()) { viewModel.gameButtonClickHandler(1, i) }
            }
        }
        Row {
            for (i in 0..2) {
                GameButton(viewModel.uiState.board[2][i].toString()) { viewModel.gameButtonClickHandler(2, i) }
            }
        }
    }
}

@Composable
fun GameButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(3.dp),
        modifier = Modifier
            .padding(bottom = 2.dp, top = 2.dp, start = 2.dp, end = 2.dp)
            .defaultMinSize(0.dp, 0.dp)
            .size(50.dp)
    ) {
        Text(text = text, color = Color.Black, fontSize = 20.sp)
    }
}