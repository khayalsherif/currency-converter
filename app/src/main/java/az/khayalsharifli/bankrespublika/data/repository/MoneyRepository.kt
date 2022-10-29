package az.khayalsharifli.bankrespublika.data.repository

import az.khayalsharifli.bankrespublika.data.local.LocalDtoItem
import kotlinx.coroutines.flow.Flow

interface MoneyRepository {
    fun observeMoney(): Flow<List<LocalDtoItem>>
    suspend fun sync()
}