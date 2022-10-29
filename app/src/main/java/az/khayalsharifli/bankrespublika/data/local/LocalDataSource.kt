package az.khayalsharifli.bankrespublika.data.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun observeMoney(): Flow<List<LocalDtoItem>>
    suspend fun insertMoney(moneyList: List<LocalDtoItem>)
}