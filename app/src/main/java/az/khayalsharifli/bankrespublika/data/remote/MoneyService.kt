package az.khayalsharifli.bankrespublika.data.remote

import retrofit2.http.GET

interface MoneyService {
    @GET("/data/data.json")
    suspend fun getMoney(): List<RemoteDtoItem>
}