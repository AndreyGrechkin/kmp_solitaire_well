package com.defey.solitairewell

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.defey.solitairewell.managers.analytics.AnalyticsManager
import com.defey.solitairewell.managers.update.UpdateManager
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val updateManager: UpdateManager by inject()
    private val analytics: AnalyticsManager by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        analytics.logAppLaunch()

        setContent {
            App()
        }

        updateManager.checkForUpdates(
            onUpdateReady = {
                updateManager.completeUpdate(
                    onComplete = {
                        Toast.makeText(this, R.string.app_update_complete, Toast.LENGTH_LONG).show()
                    },
                    onError = {}
                )
            },
            onNoUpdate = {},
            onError = {}
        )
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}