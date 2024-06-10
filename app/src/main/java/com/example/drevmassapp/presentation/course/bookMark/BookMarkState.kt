package com.example.drevmassapp.presentation.course.bookMark

import com.example.drevmassapp.data.model.BookMarkDto

sealed interface BookMarkState {
    object Initial : BookMarkState
    object Loading : BookMarkState
    data class Failure(val message: String) : BookMarkState
    data class Success(val bookMarkDto: List<BookMarkDto>) : BookMarkState
}