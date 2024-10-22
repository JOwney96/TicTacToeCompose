package gui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainUi(viewModel: ViewModel) {
    Box {
        Board(viewModel)
    }
}

@Composable
fun Board(viewModel: ViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .padding(25.dp),
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