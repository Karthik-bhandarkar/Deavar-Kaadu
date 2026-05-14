package com.example.devara_kaadu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devara_kaadu.DevaraKaaduApp
import com.example.devara_kaadu.data.model.Grove
import com.example.devara_kaadu.data.model.Species
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val groveRepo = (application as DevaraKaaduApp).groveRepository
    private val speciesRepo = (application as DevaraKaaduApp).speciesRepository

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val groveResults: StateFlow<List<Grove>> = _query
        .debounce(300)
        .flatMapLatest { q ->
            if (q.isBlank()) flowOf(emptyList()) else groveRepo.searchGroves(q)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val speciesResults: StateFlow<List<Species>> = _query
        .debounce(300)
        .flatMapLatest { q ->
            if (q.isBlank()) flowOf(emptyList()) else speciesRepo.searchSpecies(q)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setQuery(q: String) { _query.value = q }
    fun clearQuery() { _query.value = "" }
}
