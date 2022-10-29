package az.khayalsharifli.bankrespublika.data.error

import kotlinx.serialization.Serializable

@Serializable
class ServerProblemDescription(val code: String = "", val message: String = "")