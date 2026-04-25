package com.aditya.porfiliohandler.presenter.ResponseHandler

sealed class UIEvent {

    data class ShowToast(val message: String) : UIEvent()
    data class ShowError(val message: String) : UIEvent()
    object loading: UIEvent()
    object success: UIEvent()
}