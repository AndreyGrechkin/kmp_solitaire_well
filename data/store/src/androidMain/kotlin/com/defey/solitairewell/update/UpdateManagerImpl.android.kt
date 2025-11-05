package com.defey.solitairewell.update

import android.app.Activity
import android.content.Context
import com.defey.solitairewell.managers.update.UpdateManager
import ru.rustore.sdk.appupdate.manager.factory.RuStoreAppUpdateManagerFactory
import ru.rustore.sdk.appupdate.model.AppUpdateOptions
import ru.rustore.sdk.appupdate.model.AppUpdateType
import ru.rustore.sdk.appupdate.model.UpdateAvailability

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UpdateManagerImpl(context: Context) : UpdateManager {

    private val ruStoreAppUpdateManager = RuStoreAppUpdateManagerFactory.create(context)

    actual override fun checkForUpdates(
        onUpdateReady: () -> Unit,
        onNoUpdate: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        ruStoreAppUpdateManager.getAppUpdateInfo()
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability == UpdateAvailability.UPDATE_AVAILABLE) {
                    ruStoreAppUpdateManager.startUpdateFlow(
                        appUpdateInfo,
                        AppUpdateOptions.Builder()
                            .appUpdateType(AppUpdateType.SILENT)
                            .build()
                    )
                        .addOnSuccessListener { resultCode ->
                            if (resultCode == Activity.RESULT_OK) {
                                onUpdateReady()
                            } else {
                                onNoUpdate()
                            }
                        }
                        .addOnFailureListener(onError)
                } else {
                    onNoUpdate()
                }
            }
            .addOnFailureListener(onError)
    }

    actual override fun completeUpdate(
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        ruStoreAppUpdateManager.completeUpdate(
            AppUpdateOptions.Builder()
                .appUpdateType(AppUpdateType.SILENT)
                .build()
        )
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener(onError)
    }
}