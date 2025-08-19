import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import platform.UIKit.UIApplication

@Composable
actual fun Modifier.statusBarPadding(): Modifier = this
    .padding(top = if (!UIApplication.sharedApplication.isStatusBarHidden()) 24.dp else 0.dp)