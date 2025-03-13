package labs.creative.bookxpertassigment.ui.presentation.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import labs.creative.bookxpertassigment.common.Resource
import labs.creative.bookxpertassigment.common.utils.UiEvent
import labs.creative.bookxpertassigment.domain.mappers.toDomain
import labs.creative.bookxpertassigment.domain.model.UserActDetails
import labs.creative.bookxpertassigment.domain.repository.UserActRepository
import javax.inject.Inject

@HiltViewModel
class UserRecordsViewModel @Inject constructor(val repository: UserActRepository) :
    ViewModel() {
    val userActs: StateFlow<List<UserActDetails>> = repository.getAllUserActs().map { list ->
        list.map { it.toDomain() }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState: MutableStateFlow<UiEvent<List<UserActDetails>>?> = MutableStateFlow(null)
    val uiState: StateFlow<UiEvent<List<UserActDetails>>?> = _uiState

    fun fetchData() {
        viewModelScope.launch {
            repository.getUserActs().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.value = UiEvent.Failure(error = result.message)
                    }

                    is Resource.Loading -> {
                        _uiState.value = UiEvent.Loading
                    }

                    is Resource.Success -> {
                        _uiState.value = UiEvent.Success(data = result.data)
                    }
                }
            }
        }
    }

    fun updateUserRecord(
        actId: Int,
        actName: String,
        actAlternativeName: String
    ) {
        viewModelScope.launch{
            repository.updateNameById(
                actId = actId,
                actName = actName,
                actAlternativeName = actAlternativeName
            )
        }
    }

    fun deleteUserRecord(actId: Int) {
        viewModelScope.launch {
            repository.deleteById(actId = actId)
        }
    }
}