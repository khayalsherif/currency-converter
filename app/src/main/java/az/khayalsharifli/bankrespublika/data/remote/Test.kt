package az.khayalsharifli.bankrespublika.data.remote


data class TestItem(
    val cash: Cash,
    val name: String,
    val update_time: String
) {
    data class Cash(
        val eur: Eur,
        val rub: Rub,
        val usd: Usd
    ) {
        data class Eur(
            val buy: String,
            val code: String,
            val sell: String
        )

        data class Rub(
            val buy: String,
            val code: String,
            val sell: String
        )

        data class Usd(
            val buy: String,
            val code: String,
            val sell: String
        )
    }
}

