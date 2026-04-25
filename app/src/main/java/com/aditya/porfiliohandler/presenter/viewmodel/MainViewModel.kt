package com.aditya.porfiliohandler.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.porfiliohandler.domain.model.*
import com.aditya.porfiliohandler.domain.usecase.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val dashboardUseCase: DashboardUseCase,
    private val messageUseCase: MessageUseCase,
) : ViewModel() {

    private val _dashboard = MutableLiveData<Dashboard>()
    val dashboard: LiveData<Dashboard> = _dashboard

    fun getDashboardData() {
        viewModelScope.launch {
            _dashboard.value = dashboardUseCase()
        }
    }

    fun deleteMessage(message: Messages) {
        viewModelScope.launch {
            val res = messageUseCase.delete(message)
            if (res.isSuccess){

            } else {

            }
        }
    }

}
