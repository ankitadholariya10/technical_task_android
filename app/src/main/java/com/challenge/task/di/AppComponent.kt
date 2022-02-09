package com.challenge.task.di

import com.challenge.task.di.modules.AppModule
import com.challenge.task.di.modules.NetworkModule
import com.challenge.task.di.modules.ViewModelModule
import dagger.Component
import com.challenge.task.presentation.ui.UserCreationFragment
import com.challenge.task.presentation.ui.UsersFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, ViewModelModule::class, NetworkModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
    }

    fun inject(arg: UsersFragment)
    fun inject(arg: UserCreationFragment)
}
