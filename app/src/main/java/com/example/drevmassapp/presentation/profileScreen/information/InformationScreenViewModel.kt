package com.example.drevmassapp.presentation.profileScreen.information

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.domain.useCase.profile.GetSupportInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationScreenViewModel @Inject constructor(
    private val supportInfoUseCase: GetSupportInfoUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _supportInfoText = MutableLiveData<BonusInfoDto>()
    val supportInfo: LiveData<BonusInfoDto> get() = _supportInfoText

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    init {
        getSupportInfo()
    }

    private fun getSupportInfo() {
        viewModelScope.launch() {
            try {
                val result = supportInfoUseCase.getSupportInfo(bearerToken!!)
                _supportInfoText.value = result
            } catch (e: Exception) {
                Log.d("InformationScreenViewModel", "getSupportInfo:${e.message}")
            }
        }
    }
}