package az.khayalsharifli.bankrespublika.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalDtoItem::class],
    version = 1,
    exportSchema = false
)
abstract class MoneyDataBase : RoomDatabase() {
    abstract fun dao(): MoneyDao
}