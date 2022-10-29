package az.khayalsharifli.bankrespublika.data.remote


data class RemoteDtoItem(
    val cash: Cash?,
    val name: String?,
    val update_time: String?
) {
    data class Cash(
        val eur: Money?,
        val rub: Money?,
        val usd: Money?
    ) {
        data class Money(
            val buy: String? = "",
            val code: String? = "",
            val sell: String? = ""
        )
    }
}

