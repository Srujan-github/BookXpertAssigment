package labs.creative.bookxpertassigment.domain.mappers

import labs.creative.bookxpertassigment.data.remote.model.UserActDtoItem
import labs.creative.bookxpertassigment.domain.model.UserActDetails

fun UserActDtoItem.toDomain(): UserActDetails {
    return UserActDetails(
        actName = this.actName,
        actId = this.actId,
        actAlternativeName = this.actAlternativeName
    )
}