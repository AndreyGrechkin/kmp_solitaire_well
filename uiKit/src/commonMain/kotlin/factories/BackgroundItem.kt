package factories

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

sealed class BackgroundItem {
    data class ImageRes(val resource: DrawableResource) : BackgroundItem()
    data class ColorVal(val color: Color) : BackgroundItem()
}