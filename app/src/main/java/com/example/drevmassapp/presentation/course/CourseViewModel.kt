package com.example.drevmassapp.presentation.course

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.course.GetCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _courseState = MutableStateFlow<CourseState>(CourseState.Initial)
    val courseState = _courseState.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    init {
        getCourse()
    }

    private fun getCourse() {
        _courseState.update { CourseState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getCourseUseCase.getCourseList(bearerToken!!)
                _courseState.update { CourseState.Success(result) }
            } catch (e: Exception) {
                _courseState.update { CourseState.Failure(e.message.toString()) }
                Log.d("CourseViewModel", "getCourse:${e.message.toString()}")
            }
        }
    }

    fun lessonSecondToMinute(duration: Int): Int{
        return duration / 60
    }
}