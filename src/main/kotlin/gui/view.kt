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

/**
 * A Composable function representing the main UI layout of the application.
 *
 * This function sets the theme using [AppTheme] and organizes the UI components
 * in a vertical arrangement using a [Column]. The components displayed include
 * [Statistics], [Board], [NewGameButton], [ResetStatsButton], and [WinnerText].
 *
 * @param viewModel The ViewModel instance that manages the UI states and handles user interactions.
 */
@Composable
fun MainUi(viewModel: ViewModel) {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Statistics(viewModel)
            Board(viewModel)
            NewGameButton(viewModel)
            ResetStatsButton(viewModel)
            WinnerText(viewModel)
        }
    }
}

/**
 * A Composable function that displays game statistics including wins, loses, and ties.
 *
 * @param viewModel The ViewModel instance that holds the UI state with the statistics data.
 */
@Composable
fun Statistics(viewModel: ViewModel) {
    val stats = viewModel.uiState.stats
    val textModifier = Modifier.padding(start = 2.5.dp, end = 2.5.dp)

    Row(modifier = Modifier.padding(2.dp)) {
        Text(
            text = "Wins: ${stats.wins}",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = textModifier
        )
        Text(
            text = "Loses: ${stats.loses}",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = textModifier
        )
        Text(
            text = "Ties: ${stats.ties}",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = textModifier
        )
    }
}

/**
 * A Composable function that renders a game board with buttons representing the current state of the game.
 *
 * This function arranges its components in a vertical column layout, centering the content,
 * and displaying a 3x3 grid of game buttons. Each button can be clicked to trigger an action in the ViewModel.
 *
 * @param viewModel The ViewModel instance managing the UI state and handling user interactions for the game.
 */
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

/**
 * Composable function to create a game button with specified text and click action.
 *
 * @param text The text to display on the button.
 * @param onClick The callback to trigger when the button is clicked.
 */
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

/**
 * A Composable function that creates a button to start a new game.
 *
 * This function places a Button in the center of a horizontally arranged Row.
 * When the button is clicked, it invokes the ViewModel's `newGameClickHandler` method to reset the game board and start a new game.
 *
 * @param viewModel The ViewModel instance that manages the UI state and handles the click event for starting a new game.
 */
@Composable
fun NewGameButton(viewModel: ViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = viewModel::newGameClickHandler) {
            Text("New Game")
        }
    }
}

/**
 * A Composable function that displays the winning status of the game.
 *
 * This function checks the `winner` state from the given [ViewModel]'s `uiState`
 * and displays a corresponding message such as "Player Won", "Computer Won", or "Tie".
 * The message is displayed within a styled box centered in a row.
 *
 * @param viewModel The ViewModel instance that manages the UI state and holds the game outcome.
 */
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

/**
 * A Composable function that renders a button for resetting game statistics.
 *
 * This button, when clicked, triggers the `resetStatsClickHandler` function in the provided `viewModel`.
 *
 * @param viewModel The ViewModel instance that manages the UI state and provides the method for resetting statistics.
 */
@Composable
fun ResetStatsButton(viewModel: ViewModel) {
    Button(onClick = viewModel::resetStatsClickHandler) {
        Text("Reset statistics")
    }
}