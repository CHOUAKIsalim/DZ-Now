package com.example.dz_now.ui.parametres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ParametresViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is parametres Fragment"
    }
    val text: LiveData<String> = _text
}