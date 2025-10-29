package com.defey.solitairewell.managers.update

import android.app.Activity
import android.content.Context
import ru.rustore.sdk.appupdate.manager.factory.RuStoreAppUpdateManagerFactory
import ru.rustore.sdk.appupdate.model.AppUpdateOptions
import ru.rustore.sdk.appupdate.model.AppUpdateType
import ru.rustore.sdk.appupdate.model.UpdateAvailability

class RuStoreUpdateManager(context: Context) : UpdateManager {

    private val ruStoreAppUpdateManager = RuStoreAppUpdateManagerFactory.create(context)

    override fun checkForUpdates(
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

    override fun completeUpdate(
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