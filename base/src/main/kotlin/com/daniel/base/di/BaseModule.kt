package com.daniel.base.di

import android.content.SharedPreferences
import com.daniel.base.data.repository.StorageDataRepository
import com.daniel.base.domain.repository.StorageRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

const val SHARED_PREFERENCE = "app_sharedPreference"

val baseModule = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(SHARED_PREFERENCE, 0)
    }

    factoryOf(::StorageDataRepository) { bind<StorageRepository>() }
}