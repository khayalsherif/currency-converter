package az.khayalsharifli.bankrespublika.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(private val dao: MoneyDao) : LocalDataSource {

    override fun observeMoney(): Flow<List<LocalDtoItem>> {
        return dao.getMoney()
    }

    override suspend fun insertMoney(moneyList: List<LocalDtoItem>) {
        withContext(Dispatchers.IO) {
            dao.clearMoney()
            dao.insertMoney(moneyList)
        }
    }
}