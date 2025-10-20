package com.defey.solitairewell.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import model.CardState
import rememberCardSize
import theme.CardColors

@Composable
fun AnimatedBorder(
    state: CardState,
    stackSize: Int,
    isVisibleCount: Boolean,
    onAnimationComplete: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    LaunchedEffect(state) {
        if (state == CardState.SUCCESS || state == CardState.ERROR) {
            delay(350)
            onAnimationComplete()
        }
    }
    val borderColor by animateColorAsState(
        targetValue = when (state) {
            CardState.SELECTED -> CardColors.selected
            CardState.SUCCESS -> Color.Green
            CardState.ERROR -> Color.Red
            CardState.HINTED -> Color.Blue
            CardState.DEFAULT -> Color.Transparent
        },
        animationSpec = when (state) {
            CardState.SUCCESS,
            CardState.ERROR,
                -> tween(durationMillis = 300)

            else -> tween(durationMillis = 200)
        }
    )

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.0f at 0 using FastOutSlowInEasing
                0.3f at 333 using FastOutSlowInEasing
                1.0f at 666 using FastOutSlowInEasing
            },
            repeatMode = RepeatMode.Restart,
        ),
        label = "blinking_animation"
    )

    val showBlinkingEffect = state == CardState.SUCCESS || state == CardState.ERROR
    val actualBorderColor = if (showBlinkingEffect) {
        borderColor.copy(alpha = alpha)
    } else {
        borderColor
    }
    Column(modifier = Modifier.padding(4.dp)) {
        if (isVisibleCount)
            Text(
                text = stackSize.toString(),
                color = CardColors.black,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

        Box(
            modifier = Modifier
                .width(rememberCardSize())
                .aspectRatio(0.7f)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = if (state != CardState.DEFAULT) 2.dp else 0.dp,
                    color = actualBorderColor,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            content()
        }
    }
}