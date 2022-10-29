package az.khayalsharifli.bankrespublika.domain.usecase.money

import az.khayalsharifli.bankrespublika.base.BaseObserveUseCase
import az.khayalsharifli.bankrespublika.data.repository.MoneyRepository
import az.khayalsharifli.bankrespublika.data.local.LocalDtoItem
import az.khayalsharifli.bankrespublika.domain.error.ErrorConverter
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class MoneyObserveUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val moneyRepository: MoneyRepository
) : BaseObserveUseCase<Unit, List<LocalDtoItem>>(context, converter) {

    override fun createFlow(params: Unit): Flow<List<LocalDtoItem>> {
        return moneyRepository.observeMoney()
    }
}