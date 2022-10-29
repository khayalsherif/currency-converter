package az.khayalsharifli.bankrespublika.di

import az.khayalsharifli.bankrespublika.domain.usecase.money.MoneyObserveUseCase
import az.khayalsharifli.bankrespublika.domain.error.ErrorConverter
import az.khayalsharifli.bankrespublika.domain.error.ErrorConverterImpl
import az.khayalsharifli.bankrespublika.domain.usecase.money.MoneySyncUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"
const val IO_CONTEXT = "IO_CONTEXT"

val domainModule = module {

    factory {
        MoneyObserveUseCase(context = get(named(IO_CONTEXT)), converter = get(), moneyRepository = get())
    }

    factory {
        MoneySyncUseCase(context = get(named(IO_CONTEXT)), converter = get(), moneyRepository = get())
    }

    single<ErrorConverter> {
        ErrorConverterImpl(
            setOf(
                get(named(ERROR_MAPPER_NETWORK))
            )
        )
    }

}