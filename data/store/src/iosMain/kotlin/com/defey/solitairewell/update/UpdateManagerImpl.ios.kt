package com.defey.solitairewell.update

import com.defey.solitairewell.managers.update.UpdateManager

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UpdateManagerImpl : UpdateManager {
    actual override fun checkForUpdates(
        onUpdateReady: () -> Unit,
        onNoUpdate: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
    }

    actual override fun completeUpdate(
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
    }
}