import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import gui.MainUi
import gui.ViewModel
import repository.Repository

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tic Tac Toe",
        icon = BitmapPainter(image = useResource("drawable/tic-tac-toe.png", ::loadImageBitmap))
    ) {
        val repo = Repository()
        val viewModel = ViewModel(repo)
        MainUi(viewModel)
    }
}
