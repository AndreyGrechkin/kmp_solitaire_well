package com.defey.solitairewell.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.defey.solitairewell.resources.Res
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import com.defey.solitairewell.models.GameFinishStatus

@Composable
fun GameFinishAnimation(
    status: GameFinishStatus,
    onFinishAnimation: (Boolean) -> Unit
) {
    val composition by rememberLottieComposition {
        val file = when (status) {
            GameFinishStatus.WIN -> "files/win.json"
            GameFinishStatus.LOSE -> "files/lose.json"
        }
        val animationBytes = Res.readBytes(file)
        LottieCompositionSpec.JsonString(animationBytes.decodeToString())
    }
    val progress by animateLottieCompositionAsState(composition)

    Image(
        contentDescription = "Lottie animation",
        painter = rememberLottiePainter(
            composition = composition,
            progress = {
                if (progress == 1f) {
                    onFinishAnimation(true)
                }
                progress
            }
        )
    )
}