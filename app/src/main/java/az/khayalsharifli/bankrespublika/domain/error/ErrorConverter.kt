package az.khayalsharifli.bankrespublika.domain.error

interface ErrorConverter {
    fun convert(throwable: Throwable): Throwable
}