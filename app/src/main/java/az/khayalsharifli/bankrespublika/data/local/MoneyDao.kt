package az.khayalsharifli.bankrespublika.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoney(list: List<LocalDtoItem>)

    @Query("SELECT * FROM money")
    fun getMoney(): Flow<List<LocalDtoItem>>

    @Query("DELETE FROM money")
    fun clearMoney()
}