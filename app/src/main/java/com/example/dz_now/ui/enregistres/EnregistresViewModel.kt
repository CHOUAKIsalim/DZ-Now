package com.example.dz_now.ui.enregistres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnregistresViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is enregistres Fragment"
    }
    val text: LiveData<String> = _text
}