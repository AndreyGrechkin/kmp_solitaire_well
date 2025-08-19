package base

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNavHost(
    router: Router,
    currentScreen: Any,
    content: @Composable AnimatedContentScope.(Any) -> Unit
) {
    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            router.getTransitionForScreen(targetState).asContentTransform()
        },
        label = "ScreenTransition"
    ) { screen ->
        content(screen)
    }
}