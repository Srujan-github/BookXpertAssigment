package labs.creative.bookxpertassigment.data.remote.model


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Keep
@Entity(tableName = "user_act_table")
data class UserActDtoItem(
    @SerializedName("ActName")
    val actName: String,
    @SerializedName("actid")
    @PrimaryKey
    val actId: Int,
    val actAlternativeName: String? = null
)