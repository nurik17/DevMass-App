package com.example.drevmassapp.presentation.profileScreen.myPoints

import com.example.drevmassapp.data.model.BonusDto

sealed interface PointsState {
    object Initial : PointsState
    object Loading : PointsState
    data class Failure(val message: String) : PointsState
    data class Success(val bonus: BonusDto) : PointsState
}