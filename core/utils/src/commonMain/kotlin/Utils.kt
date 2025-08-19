import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp




@Composable
fun getScreenMetrics(): ScreenMetrics {
    val density = LocalDensity.current.density
    val windowInfo = LocalWindowInfo.current

    return ScreenMetrics(
        widthPx = windowInfo.containerSize.width,
        heightPx = windowInfo.containerSize.height,
        density = density,
        widthDp = (windowInfo.containerSize.width / density).dp,
        heightDp = (windowInfo.containerSize.height / density).dp
    )
}

data class ScreenMetrics(
    val widthPx: Int,
    val heightPx: Int,
    val density: Float,
    val widthDp: Dp,
    val heightDp: Dp
)

fun debugLog(message: String) {
    println("DEBUG: $message")
}

fun Int.pxToDp(density: Float): Dp = (this / density).dp

fun Float.pxToDp(density: Float): Dp = (this / density).dp


@Composable
fun getDensity(): Float {
    return LocalDensity.current.density
}


