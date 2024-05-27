package com.example.drevmassapp.presentation.profileScreen.promocode

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drevmassapp.domain.useCase.profile.GetPromocodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromocodeScreenViewModel @Inject constructor(
    private val getPromocodeUseCase: GetPromocodeUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _promocodeState = MutableStateFlow<PromocodeState>(PromocodeState.Initial)
    val promocodeState = _promocodeState.asStateFlow()

    private val bearerToken = sharedPreferences.getString("accessToken", null)

    init {
        getPromocode()
    }
    private fun getPromocode() {
        _promocodeState.update { PromocodeState.Loading }
        viewModelScope.launch {
            try {
                val result = getPromocodeUseCase.getPromocodeInfo(bearerToken!!)
                _promocodeState.update { PromocodeState.Success(result) }
            } catch (e: Exception) {
                _promocodeState.update { PromocodeState.Failure(e.message.toString()) }
            }
        }
    }

    fun shareText(context: Context, shareText: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
}