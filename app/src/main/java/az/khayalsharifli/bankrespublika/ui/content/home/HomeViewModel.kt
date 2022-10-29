package az.khayalsharifli.bankrespublika.ui.content.home

import androidx.lifecycle.viewModelScope
import az.khayalsharifli.bankrespublika.base.BaseViewModel
import az.khayalsharifli.bankrespublika.data.local.LocalDtoItem
import az.khayalsharifli.bankrespublika.domain.usecase.money.MoneyObserveUseCase
import az.khayalsharifli.bankrespublika.domain.usecase.money.MoneySyncUseCase
import az.khayalsharifli.bankrespublika.tools.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val moneyObserveUseCase: MoneyObserveUseCase,
    moneySyncUseCase: MoneySyncUseCase
) : BaseViewModel() {

    data class SelectedCurrencyCodes(val sellRate: String = "", val buyRate: String = "")

    val selectedCurrencyCodes = SingleLiveEvent<SelectedCurrencyCodes>()

    private var _moneyResponse = MutableStateFlow(emptyList<LocalDtoItem>())
    val moneyResponse: StateFlow<List<LocalDtoItem>>
        get() = _moneyResponse.asStateFlow()

    init {
        getEpicData()
        moneySyncUseCase.launch(Unit)
    }

    private fun getEpicData() = viewModelScope.launch {
        moneyObserveUseCase.execute(Unit).collect {
            _moneyResponse.emit(it)
        }
    }
}