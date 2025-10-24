package com.defey.solitairewell.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.defey.solitairewell.SettingsViewModel
import androidx.lifecycle.SavedStateHandle

val settingsModule
    get() = module {
        includes(settingsDomainModule)
        viewModel { (savedStateHandle: SavedStateHandle) ->
            SettingsViewModel(savedStateHandle, get(), get(), get())
        }
    }