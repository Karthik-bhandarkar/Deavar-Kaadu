package com.example.devara_kaadu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devara_kaadu.DevaraKaaduApp
import com.example.devara_kaadu.data.model.Grove
import com.example.devara_kaadu.data.model.GroveType
import com.example.devara_kaadu.data.repository.GroveRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class GroveViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: GroveRepository = (application as DevaraKaaduApp).groveRepository

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedType = MutableStateFlow(GroveType.ALL)
    val selectedType: StateFlow<GroveType> = _selectedType

    private val _selectedGrove = MutableStateFlow<Grove?>(null)
    val selectedGrove: StateFlow<Grove?> = _selectedGrove

    private val _isGridView = MutableStateFlow(true)
    val isGridView: StateFlow<Boolean> = _isGridView

    /** Reactive grove list filtered by type and search query. */
    val groves: StateFlow<List<Grove>> = combine(_searchQuery, _selectedType) { query, type ->
        Pair(query, type)
    }.flatMapLatest { (query, type) ->
        when {
            query.isNotBlank() -> repo.searchGroves(query)
            type == GroveType.ALL -> repo.getAllGroves()
            else -> repo.getGrovesByType(type.displayName)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val visitedCount: StateFlow<Int> = flow {
        emit(repo.getVisitedCount())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun setSearchQuery(q: String) { _searchQuery.value = q }
    fun setSelectedType(t: GroveType) { _selectedType.value = t }
    fun toggleViewMode() { _isGridView.value = !_isGridView.value }

    fun loadGroveById(id: Int) = viewModelScope.launch {
        _selectedGrove.value = repo.getGroveById(id)
    }

    fun markAsVisited(groveId: Int) = viewModelScope.launch {
        repo.markAsVisited(groveId)
        // Also increment user progress points
        (getApplication<DevaraKaaduApp>()).userProgressRepository.incrementGrovesVisited()
    }
}
