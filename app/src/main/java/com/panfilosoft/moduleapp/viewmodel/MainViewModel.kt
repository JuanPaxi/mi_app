package com.panfilosoft.moduleapp.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panfilosoft.moduleapp.state.UserIntent
import com.panfilosoft.moduleapp.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel : ViewModel() {

    private val _state = MutableStateFlow<ViewState>(ViewState.Idle)
    val state: StateFlow<ViewState> get() = _state

    fun processIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.LoadData -> loadData()
            is UserIntent.Search -> search(intent.query)
            is UserIntent.Refresh -> refreshData()
        }
    }

    private fun loadData() {
        _state.value = ViewState.Loading
        viewModelScope.launch {
            // Simulación de carga de datos
            kotlinx.coroutines.delay(1000)
            _state.value = ViewState.Data(listOf("Item 1", "Item 2", "Item 3"))
        }
    }

    private fun search(query: String) {
        _state.value = ViewState.Loading
        viewModelScope.launch {
            kotlinx.coroutines.delay(500)
            // Filtrar resultados simulados
            val filteredItems = listOf("Item 1", "Item 2", "Item 3").filter { it.contains(query) }
            _state.value = ViewState.Data(filteredItems)
        }
    }

    private fun refreshData() {
        loadData()
    }
}