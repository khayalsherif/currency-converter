package az.khayalsharifli.bankrespublika.domain.error

fun interface ErrorMapper {
    fun mapError(throwable: Throwable): Throwable
}