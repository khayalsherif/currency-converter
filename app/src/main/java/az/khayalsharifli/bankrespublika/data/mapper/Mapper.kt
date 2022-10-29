package az.khayalsharifli.bankrespublika.data.mapper

interface Mapper<REMOTE, LOCAL> {
    fun fromRemoteToLocal(remote: REMOTE): LOCAL
}