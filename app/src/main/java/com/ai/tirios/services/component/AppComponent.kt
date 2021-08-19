package com.ai.tirios.services.component

import com.ai.tirios.MainApplication
import com.ai.tirios.services.module.ActivityModule
import com.ai.tirios.services.module.ApiModule
import com.ai.tirios.services.module.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by Maruthi Ram Yadav on 10/9/2020.
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class, ApiModule::class])
interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MainApplication>()
}
