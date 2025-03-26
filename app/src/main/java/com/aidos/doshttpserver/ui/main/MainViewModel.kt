package com.aidos.doshttpserver.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aidos.doshttpserver.data.repository.AppConfigRepository
import com.aidos.doshttpserver.data.repository.CallInfoRepository
import com.aidos.doshttpserver.ui.main.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val callInfoRepository: CallInfoRepository,
    val appConfigRepository: AppConfigRepository
) : ViewModel() {

    private val _mainViewState = MutableStateFlow(MainViewState())
    val callItemsState = callInfoRepository.getCallItemsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = listOf()
        )

    init {
        viewModelScope.launch {
            appConfigRepository.getConfigFlow().collect {
                _mainViewState.update { it.copy(serverAddress = it.serverAddress) }
            }
        }
    }

    fun setIsServerRunning(isServerRunning: Boolean) {
        _mainViewState.update { it.copy(isServerRunning = isServerRunning) }
    }
}