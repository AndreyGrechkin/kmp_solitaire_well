package com.defey.solitairewell.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.defey.solitairewell.model.CardState
import com.defey.solitairewell.theme.CardColors

@Composable
fun EmptyCardSlot(
    modifier: Modifier = Modifier,
    stackSize: Int,
    state: CardState,
    isVisibleCount: Boolean,
    onAnimationComplete: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    AnimatedBorder(
        state = state,
        stackSize = stackSize,
        isVisibleCount = isVisibleCount,
        onAnimationComplete = onAnimationComplete
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
                .background(color = CardColors.defaultEmptyCardSlot)
        )
    }
}