package edu.nd.pmcburne.hello

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.nd.pmcburne.hello.data.AppDatabase
import edu.nd.pmcburne.hello.data.LocationEntity
import edu.nd.pmcburne.hello.data.PlacemarkApiService
import edu.nd.pmcburne.hello.data.PlacemarkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PlacemarkRepository(
        dao = AppDatabase.getInstance(application).locationDao(),
        api = PlacemarkApiService.create()
    )


    val selectedTag = MutableStateFlow("core")

    val tags: StateFlow<List<String>> = repo.allTags()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val locations: StateFlow<List<LocationEntity>> = selectedTag
        .flatMapLatest { tag -> repo.locationsByTag(tag) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val isLoading = MutableStateFlow(true)

    val error = MutableStateFlow<String?>(null)


    init {
        viewModelScope.launch {
            try {
                repo.syncIfNeeded()
            } catch (e: Exception) {
                error.value = "Failed to load data: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun selectTag(tag: String) {
        selectedTag.value = tag
    }
}