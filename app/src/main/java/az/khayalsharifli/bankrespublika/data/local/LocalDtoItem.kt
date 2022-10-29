package az.khayalsharifli.bankrespublika.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "money")
class LocalDtoItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Embedded val cash: Cash,
    val name: String,
    val update_time: String
) {
    data class Cash(
        @Embedded val eur: Eur,
        @Embedded val rub: Rub,
        @Embedded val usd: Usd
    ) {
        data class Eur(
            @ColumnInfo(name = "buyEur")val buy: String,
            @ColumnInfo(name = "codeEur")val code: String,
            @ColumnInfo(name = "sellEur") val sell: String
        )

        data class Rub(
            @ColumnInfo(name = "buyRub") val buy: String,
            @ColumnInfo(name = "codeRub") val code: String,
            @ColumnInfo(name = "sellRub") val sell: String
        )

        data class Usd(
            @ColumnInfo(name = "buyUsd")val buy: String,
            @ColumnInfo(name = "codeUsd")val code: String,
            @ColumnInfo(name = "sellUsd")val sell: String
        )
    }
}
