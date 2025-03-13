package labs.creative.bookxpertassigment.domain.model

data class UserActDetails(
    val actName: String,
    val actId: Int,
    val actAlternativeName: String? = null
)
