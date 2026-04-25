package com.aditya.porfiliohandler.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.porfiliohandler.domain.model.*
import com.aditya.porfiliohandler.domain.usecase.*
import com.aditya.porfiliohandler.presenter.ResponseHandler.UIEvent
import kotlinx.coroutines.launch

class MainViewModel(
    private val dashboardUseCase: DashboardUseCase,
    private val messageUseCase: MessageUseCase,
) : ViewModel() {

    private val _dashboard = MutableLiveData<Dashboard>()
    val dashboard: LiveData<Dashboard> = _dashboard

    private val _uiEvent = MutableLiveData<UIEvent>()
    val uiEvent : LiveData<UIEvent> = _uiEvent

    fun getDashboardData() {
        viewModelScope.launch {
            _dashboard.value = dashboardUseCase()
        }
    }

    fun deleteMessage(message: Messages) {
        viewModelScope.launch {

            _uiEvent.value = UIEvent.loading

            val res = messageUseCase.delete(message)
            if (res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while deleting the message")
            }
        }
    }

    fun readMessage(messages: Messages){
        viewModelScope.launch {
            _uiEvent.value = UIEvent.loading

            val res = messageUseCase.read(messages)
            if (res.isSuccess){
                _uiEvent.value = UIEvent.success
            } else {
                _uiEvent.value = UIEvent.ShowError("Error while updating the message")
            }

        }
    }

}
