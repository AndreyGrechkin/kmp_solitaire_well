import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
actual fun Modifier.statusBarPadding(): Modifier = this
    .padding(top = 0.dp)