package az.khayalsharifli.bankrespublika.di

import az.khayalsharifli.bankrespublika.ui.content.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        HomeViewModel(
            moneyObserveUseCase = get(),
            moneySyncUseCase = get()
        )
    }
}