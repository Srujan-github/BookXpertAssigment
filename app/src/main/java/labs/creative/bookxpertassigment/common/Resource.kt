package labs.creative.bookxpertassigment.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val message: String, val data: T? = null) : Resource<T>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success(data=$data)"
            is Error -> "Error(message=$message, data=$data)"
            Loading -> "Loading"
        }
    }
}