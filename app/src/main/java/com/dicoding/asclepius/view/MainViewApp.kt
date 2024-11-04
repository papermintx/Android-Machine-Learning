package com.dicoding.asclepius.view

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewApp @Inject constructor() : ViewModel() {
    var currentFragment: String? = null
    var bottomNavPosition: Int = 0
}