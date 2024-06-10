package com.example.drevmassapp.presentation.course.bookMark

import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.BookMarkDto
import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.navigation.MainNavGraph
import com.example.drevmassapp.presentation.course.detail.CourseDetailScreenViewModel
import com.example.drevmassapp.presentation.course.detail.LessonsItem
import com.example.drevmassapp.presentation.course.detail.RowWithLessonInfo2
import com.example.drevmassapp.presentation.course.detail.VideoImageBox
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkScreen(
    navigateBack: () -> Unit,
    viewModel: BookMarkViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit){
        viewModel.getBookMark()
    }

    val bookMarkState = viewModel.bookMarkState.collectAsStateWithLifecycle()
    val currentState = bookMarkState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(id = R.string.course),
                        color = Brand900,
                        style = typography.l17sfT600,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = Brand900
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
            )
        },
    ) { paddingValues ->
        when(currentState){
            is BookMarkState.Initial ->{
                EmptyBookMarkScreenContent(paddingValues = paddingValues)
            }
            is BookMarkState.Success ->{
                SuccessBookMarkScreenContent(
                    paddingValues = paddingValues,
                    listBookMark = currentState.bookMarkDto[0].lessons,
                    item = currentState.bookMarkDto[0]
                )
            }
            is BookMarkState.Failure ->{

            }
            is BookMarkState.Loading ->{
                ProgressBlock()
            }
        }
    }
}
@Composable
fun EmptyBookMarkScreenContent(
    paddingValues: PaddingValues
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues = paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 4.dp),
            text = stringResource(id = R.string.my_bookMarks),
            style = typography.l28sfD700
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.3f))
        Image(
            modifier = Modifier.size(112.dp),
            painter = painterResource(id = R.drawable.image_empty_bookmark),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.bookmark_empty),
            style = typography.l17sfT600,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = stringResource(id = R.string.bookmark_empty_subTitle),
            style = typography.l17sfT600,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun SuccessBookMarkScreenContent(
    paddingValues: PaddingValues,
    listBookMark: List<Lesson>,
    item: BookMarkDto
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues = paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 4.dp),
            text = stringResource(id = R.string.my_bookMarks),
            style = typography.l28sfD700
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            items(items = listBookMark){
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 16.dp),
                    text = item.courseName,
                    style = typography.l20sfD600
                )
                LessonsItem(item = it)
            }
        }
    }
}

