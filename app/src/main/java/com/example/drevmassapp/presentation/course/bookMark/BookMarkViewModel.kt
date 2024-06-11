package com.example.drevmassapp.presentation.course.bookMark

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.course.GetBookMarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val getBookMarkUseCase: GetBookMarkUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _bookMarkState = MutableStateFlow<BookMarkState>(BookMarkState.Initial)
    val bookMarkState = _bookMarkState.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    fun getBookMark() {
        _bookMarkState.update { BookMarkState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getBookMarkUseCase.getBookMarks(bearerToken!!)
                _bookMarkState.update { BookMarkState.Success(result) }
            } catch (e: Exception) {
                _bookMarkState.update { BookMarkState.Failure(e.message.toString()) }
                Log.d("BookMarkViewModel", "getBookMark: ${e.message.toString()}")
            }
        }
    }
}