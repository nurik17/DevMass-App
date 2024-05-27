package com.example.drevmassapp.presentation.profileScreen.myPoints

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.domain.useCase.profile.GetBonusInfoUseCase
import com.example.drevmassapp.domain.useCase.profile.GetBonusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyPointsViewModel @Inject constructor(
    private val bonusUseCase: GetBonusUseCase,
    private val getBonusInfoUseCase: GetBonusInfoUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _pointsState = MutableStateFlow<PointsState>(PointsState.Initial)
    val pointsState = _pointsState.asStateFlow()

    private val _bonusInfo = MutableLiveData<BonusInfoDto>()
    val bonusInfo: LiveData<BonusInfoDto> get() = _bonusInfo

    private val bearerToken = sharedPreferences.getString("accessToken",null)
    init {
        getBonus()
    }

    private fun getBonus() {
        _pointsState.update { PointsState.Loading }
        viewModelScope.launch {
            try {
                val result = bonusUseCase.getBonus(bearerToken!!)
                _pointsState.update { PointsState.Success(result) }
            } catch (e: Exception) {
                _pointsState.update { PointsState.Failure(e.message.toString()) }
            }
        }
    }

    fun getBonusInfo() {
        viewModelScope.launch {
            try {
                val bonusInfo = getBonusInfoUseCase.getBonusInfo(bearerToken!!)
                _bonusInfo.value = bonusInfo
            } catch (e: Exception) {
                Log.d("MyPointsViewModel", e.message.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ru", "RU"))

        val date = LocalDateTime.parse(inputDate, inputFormatter)
        return date.format(outputFormatter)
    }
}