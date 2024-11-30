package com.panfilosoft.moduleapp.state

sealed class ViewState {
    object Idle : ViewState()
    object Loading : ViewState()
    data class Data(val items: List<String>) : ViewState()
    data class Error(val message: String) : ViewState()
}


sealed class UserIntent {
    object LoadData : UserIntent()
    data class Search(val query: String) : UserIntent()
    object Refresh : UserIntent()
}
