package com.example.drevmassapp.presentation.course.lessonDetail

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.course.GetLessonDetailByIdUseCase
import com.example.drevmassapp.domain.useCase.course.LessonCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonDetailViewModel @Inject constructor(
    private val getLessonDetailByIdUseCase: GetLessonDetailByIdUseCase,
    private val lessonCompleteUseCase: dagger.Lazy<LessonCompleteUseCase>,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _lessonDetailState = MutableStateFlow<LessonDetailState>(LessonDetailState.Initial)
    val lessonDetailState = _lessonDetailState.asStateFlow()

    private val _isIconChecked = MutableStateFlow(false)
    val isIconChecked = _isIconChecked.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    fun getLessonDetailById(courseId: Int, lessonId: Int) {
        _lessonDetailState.update { LessonDetailState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getLessonDetailByIdUseCase.getLessonsDetailById(
                    bearerToken!!,
                    courseId,
                    lessonId
                )
                _lessonDetailState.update { LessonDetailState.Success(result) }
            } catch (e: Exception) {
                _lessonDetailState.update { LessonDetailState.Failure(e.message.toString()) }
                Log.d("LessonDetailViewModel", "getLessonDetailById: ${e.message.toString()}")
            }
        }
    }

    fun lessonComplete(lessonId: Int, courseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =
                    lessonCompleteUseCase.get().lessonComplete(bearerToken!!, lessonId, courseId)
                Log.d("LessonDetailViewModel", "result: $result")

            } catch (e: Exception) {
                Log.d("LessonDetailViewModel", "lessonComplete:${e.message.toString()}")
            }
        }
    }

    fun onIconCheckedChange(isChecked: Boolean) {
        _isIconChecked.update { isChecked }

    }

    fun lessonSecondToMinute(duration: Int): Int {
        return duration / 60
    }
}