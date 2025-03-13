package labs.creative.bookxpertassigment.common.utils

import labs.creative.bookxpertassigment.data.remote.model.UserActDtoItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.toJson(): List<UserActDtoItem> {
    val gson = Gson()
    return try {
        val actualJson = if (this.startsWith("\"") && this.endsWith("\"")) {
            gson.fromJson(this, String::class.java)
        } else {
            this
        }
        val listType = object : TypeToken<List<UserActDtoItem>>() {}.type
        val actList: List<UserActDtoItem> = gson.fromJson(actualJson, listType)
        actList
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}
