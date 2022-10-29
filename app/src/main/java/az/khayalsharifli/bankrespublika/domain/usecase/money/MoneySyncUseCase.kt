package az.khayalsharifli.bankrespublika.domain.usecase.money

import az.khayalsharifli.bankrespublika.base.BaseSyncUseCase
import az.khayalsharifli.bankrespublika.data.repository.MoneyRepository
import az.khayalsharifli.bankrespublika.domain.error.ErrorConverter
import kotlin.coroutines.CoroutineContext

class MoneySyncUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val moneyRepository: MoneyRepository
) : BaseSyncUseCase<Unit, Unit>(context, converter) {
    override suspend fun executeOnBackground(params: Unit) {
        moneyRepository.sync()
    }

}