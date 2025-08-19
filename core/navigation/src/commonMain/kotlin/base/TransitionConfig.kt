package base

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

data class TransitionConfig(
    val enter: EnterTransition = fadeIn(),
    val exit: ExitTransition = fadeOut(),
    val durationMs: Int = 300
) {
    fun asContentTransform(): ContentTransform {
        return enter.togetherWith(exit)
    }
    companion object {
        /** Горизонтальный слайд (как в Android Navigation) */
        val SLIDE_HORIZONTAL = TransitionConfig(
            enter = slideInHorizontally(
                animationSpec = tween(300),
                initialOffsetX = { fullWidth -> fullWidth }
            ),
            exit = slideOutHorizontally(
                animationSpec = tween(300),
                targetOffsetX = { fullWidth -> -fullWidth }
            )
        )

        /** Вертикальный слайд */
        val SLIDE_VERTICAL = TransitionConfig(
            enter = slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { fullHeight -> fullHeight }
            ),
            exit = slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { fullHeight -> -fullHeight }
            )
        )

        /** Плавное появление/исчезновение */
        val FADE = TransitionConfig(
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        )

        /** Масштабирование + fade */
        val SCALE = TransitionConfig(
            enter = fadeIn(animationSpec = tween(300)) +
                    scaleIn(animationSpec = tween(300), initialScale = 0.9f),
            exit = fadeOut(animationSpec = tween(300)) +
                    scaleOut(animationSpec = tween(300), targetScale = 1.1f)
        )
    }
}