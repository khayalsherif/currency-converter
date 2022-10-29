package az.khayalsharifli.bankrespublika

import android.app.Application
import az.khayalsharifli.bankrespublika.di.dataModule
import az.khayalsharifli.bankrespublika.di.domainModule
import az.khayalsharifli.bankrespublika.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = this@App)

            properties(
                mapOf("base" to "https://www.azn.az/")
            )

            val modules = listOf(dataModule, domainModule, uiModule)
            modules(modules)
        }
    }
}