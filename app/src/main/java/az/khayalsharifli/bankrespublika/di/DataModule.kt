package az.khayalsharifli.bankrespublika.di

import androidx.room.Room
import az.khayalsharifli.bankrespublika.BuildConfig
import az.khayalsharifli.bankrespublika.data.error.RemoteErrorMapper
import az.khayalsharifli.bankrespublika.data.repository.MoneyRepository
import az.khayalsharifli.bankrespublika.data.repository.MoneyRepositoryImpl
import az.khayalsharifli.bankrespublika.data.local.LocalDataSource
import az.khayalsharifli.bankrespublika.data.local.LocalDataSourceImpl
import az.khayalsharifli.bankrespublika.data.local.MoneyDataBase
import az.khayalsharifli.bankrespublika.data.mapper.MoneyMapper
import az.khayalsharifli.bankrespublika.data.remote.MoneyService
import az.khayalsharifli.bankrespublika.domain.error.ErrorMapper
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

val dataModule = module {

    single<CoroutineContext>(named(IO_CONTEXT)) { Dispatchers.IO }

    ////////////////////////////////////// REMOTE //////////////////////////////////////////////////

    single {
        val client = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logger)
        }

        client.build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(getProperty("base"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    factory<MoneyService> { get<Retrofit>().create(MoneyService::class.java) }

    factory<MoneyRepository> {
        MoneyRepositoryImpl(
            service = get(),
            localDataSource = get(),
            moneyMapper = get()
        )
    }

    factory { MoneyMapper() }

    ////////////////////////////////////// LOCAL //////////////////////////////////////////////////

    single { get<MoneyDataBase>().dao() }

    single<LocalDataSource> { LocalDataSourceImpl(dao = get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            MoneyDataBase::class.java,
            "money"
        ).fallbackToDestructiveMigration()
            .build()
    }

    factory<ErrorMapper>(named(ERROR_MAPPER_NETWORK)) { RemoteErrorMapper() }


}