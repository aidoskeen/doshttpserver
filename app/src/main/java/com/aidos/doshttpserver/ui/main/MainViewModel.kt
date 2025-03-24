package com.aidos.doshttpserver.ui.main

import androidx.lifecycle.ViewModel
import com.aidos.doshttpserver.ui.main.viewstate.CallItem
import com.aidos.doshttpserver.ui.main.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val viewState = _mainViewState.asStateFlow()

    private val _callItemsState = MutableStateFlow(listOf<CallItem>())
    val callItemsState = _callItemsState.asStateFlow()
}