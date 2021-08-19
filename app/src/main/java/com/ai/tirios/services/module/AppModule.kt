package com.ai.tirios.services.module

import android.content.Context
import com.ai.tirios.MainApplication
import com.ai.tirios.PermissionUtils
import com.ai.tirios.utils.Constants.SharedPref.Companion.SHARED_PREF
import com.ai.tirios.utils.Utilities
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ai.tirios.data.DataManager
import com.ai.tirios.data.IDataManager
import com.ai.tirios.preference.IPreferencesHelper
import com.ai.tirios.preference.PreferencesHelper
import com.ai.tirios.services.PreferenceInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: MainApplication): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideDataManager(mDataManager: DataManager): IDataManager {
        return mDataManager
    }

    //          Regarding Preference
    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return SHARED_PREF
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(mPreferencesHelper: PreferencesHelper): IPreferencesHelper {
        return mPreferencesHelper
    }

    //          Regarding API Call
    @Provides
    @Singleton
    internal fun provideGSON(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    //          Other Project Components
    @Provides
    @Singleton
    internal fun providePermissionUtils(): PermissionUtils {
        return PermissionUtils
    }

    @Provides
    @Singleton
    internal fun providesUtility(context: Context): Utilities {
        return Utilities(context)
    }
}