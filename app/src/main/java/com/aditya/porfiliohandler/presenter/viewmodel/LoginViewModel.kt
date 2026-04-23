package com.aditya.porfiliohandler.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.porfiliohandler.domain.model.User
import com.aditya.porfiliohandler.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableLiveData<Result<User>>(null)
    val state: LiveData<Result<User>> = _state

    fun login(email: String, password: String) {

        viewModelScope.launch {
            val result = loginUseCase(email, password)
            _state.value = result
        }

    }

}