package com.example.favourr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _connectionId = MutableLiveData<String>()
    val connectionId: LiveData<String> = _connectionId

    fun setConnectionId(id: String) {
        _connectionId.value = id
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun setState(newState: State) {
        _state.value = newState
    }

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun setName(newName: String) {
        _name.value = newName
    }

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    fun setCity(newCity: String) {
        _city.value = newCity
    }

    enum class State {
        SEARCHING, CONNECTED
    }
}