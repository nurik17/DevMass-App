package com.example.drevmassapp.presentation.course.detail

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.useCase.course.GetCourseByIdUseCase
import com.example.drevmassapp.domain.useCase.course.GetLessonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailScreenViewModel @Inject constructor(
    private val getCourseByIdUseCase: GetCourseByIdUseCase,
    private val getLessonListUseCase: GetLessonListUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _courseDetailState = MutableStateFlow<CourseDetailState>(CourseDetailState.Initial)
    val courseDetailState = _courseDetailState.asStateFlow()

    private val _lessonLoading = MutableStateFlow(false)
    val lessonLoading = _lessonLoading.asStateFlow()

    private val _isSwitchChecked = MutableStateFlow(false)
    val isSwitchChecked: StateFlow<Boolean> = _isSwitchChecked

    private val _isDayOfLessonSheet = MutableStateFlow(false)
    val isDayOfLessonSheet: StateFlow<Boolean> = _isDayOfLessonSheet

    private val _isTimePickerOpen = MutableStateFlow(false)
    val isTimePickerOpen: StateFlow<Boolean> = _isTimePickerOpen

    private val _clickedDays = MutableStateFlow<Map<Int, List<String>>>(emptyMap())
    val clickedDays: StateFlow<Map<Int, List<String>>> = _clickedDays



    private val bearerToken = sharedPreferences.getString("accessToken", null)


    init {
        _clickedDays.value = loadClickedDays().toMutableMap()

    }

    fun getCourseDetailsById(courseId: Int) {
        _courseDetailState.update { CourseDetailState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getCourseByIdUseCase.getCourseById(bearerToken!!, courseId)
                _courseDetailState.update { CourseDetailState.Success(result) }
                _isSwitchChecked.value = loadSwitchState(courseId)
            } catch (e: Exception) {
                _courseDetailState.update { CourseDetailState.Failure(e.message.toString()) }
                Log.d("CourseDetailScreenViewModel", "getCourseDetailsById:${e.message.toString()}")
            }
        }
    }

    fun getLessonList(courseId: Int) {
        _lessonLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getLessonListUseCase.getLessonsById(bearerToken!!, courseId)
            } catch (e: Exception) {
                Log.d("CourseDetailScreenViewModel", "getLessonListUseCase:${e.message.toString()}")
            }
        }
    }

    private fun loadSwitchState(courseId: Int): Boolean {
        return sharedPreferences.getBoolean("switch_state_$courseId", false)
    }

    fun onCheckedChange(courseId: Int, isChecked: Boolean) {
        _isSwitchChecked.value = isChecked
        saveSwitchState(courseId, isChecked)
    }

    private fun saveSwitchState(courseId: Int, isChecked: Boolean) {
        sharedPreferences.edit().putBoolean("switch_state_$courseId", isChecked).apply()
    }

    fun setBottomSheetState(isVisible: Boolean) {
        viewModelScope.launch {
            _isDayOfLessonSheet.update { (isVisible) }
        }
    }

    fun setTimePickerState(isVisible: Boolean) {
        viewModelScope.launch {
            _isTimePickerOpen.update { (isVisible) }
        }
    }

    fun onDayOfLessonItemClick(courseId: Int, text: String) {
        val updatedList = _clickedDays.value.toMutableMap()
        val currentList = updatedList[courseId]?.toMutableList() ?: mutableListOf()

        if (currentList.contains(text)) {
            currentList.remove(text)
        } else {
            currentList.add(text)
        }

        updatedList[courseId] = currentList
        _clickedDays.value = updatedList.toMap()

        // Save the updated clicked days
        saveClickedDays()
    }

    private fun loadClickedDays(): Map<Int, List<String>> {
        val savedItems = sharedPreferences.all.entries
            .filter { it.key.startsWith("clicked_days_") }
            .map { entry ->
                val courseId = entry.key.substring("clicked_days_".length).toInt()
                val days = sharedPreferences.getStringSet(entry.key, emptySet())?.toList() ?: emptyList()
                courseId to days
            }
            .toMap()

        return savedItems
    }

    fun saveClickedDays() {
        _clickedDays.value.forEach { (courseId, days) ->
            sharedPreferences.edit().putStringSet("clicked_days_$courseId", days.toSet()).apply()
        }
    }



    fun deleteListOfDays(courseId: Int) {
        val updatedList = _clickedDays.value.toMutableMap()
        updatedList.remove(courseId)
        _clickedDays.value = updatedList.toMap()

        sharedPreferences.edit().remove("clicked_days_$courseId").apply()
    }

    fun getShortTextForDays(courseId: Int): String {
        val dayOrder = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
        val dayAbbreviations = mapOf(
            "Понедельник" to "Пн",
            "Вторник" to "Вт",
            "Среда" to "Ср",
            "Четверг" to "Чт",
            "Пятница" to "Пт",
            "Суббота" to "Сб",
            "Воскресенье" to "Вс"
        )

        return clickedDays.value[courseId]
            ?.sortedWith(compareBy { dayOrder.indexOf(dayAbbreviations[it] ?: it) })
            ?.joinToString(", ") { dayAbbreviations[it] ?: it }
            ?: ""
    }

    fun saveTimerText(courseId: Int, timerText: String) {
        val editor = sharedPreferences.edit()
        editor.putString("timerText_$courseId", timerText)
        editor.apply()
    }

    fun getTimerText(courseId: Int): String {
        return sharedPreferences.getString("timerText_$courseId", "") ?: ""
    }

    fun lessonSecondToMinute(duration: Int): Int {
        return duration / 60
    }
}