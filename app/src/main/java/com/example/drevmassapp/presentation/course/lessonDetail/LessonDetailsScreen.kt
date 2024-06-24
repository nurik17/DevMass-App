package com.example.drevmassapp.presentation.course.lessonDetail

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.data.model.Product
import com.example.drevmassapp.presentation.catalog.detail.RecommendBlock
import com.example.drevmassapp.presentation.course.detail.VideoImageBox
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.typography
import com.example.drevmassapp.util.Constant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailsScreen(
    courseId: String?,
    lessonId: String?,
    navigateBack: () -> Unit,
    navigateToVideoPlayerScreen: (String) -> Unit,
    viewModel: LessonDetailViewModel = hiltViewModel()
) {
    val detailState = viewModel.lessonDetailState.collectAsStateWithLifecycle()
    val currentState = detailState.value

    val isIconChecked by viewModel.isIconChecked.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        courseId?.let {
            lessonId?.let { it1 ->
                viewModel.getLessonDetailById(
                    it.toInt(),
                    it1.toInt()
                )
            }
        }
        Log.d("LessonDetailsScreen", "courseId:$courseId")
        Log.d("LessonDetailsScreen", "lessonId:$lessonId")
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (currentState is LessonDetailState.Success) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = currentState.lesson.title,
                            color = Color.Black,
                            style = typography.l17sfT600,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

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
                actions = {
                    IconButton(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = { }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            when (currentState) {
                is LessonDetailState.Loading -> {
                    ProgressBlock()
                }

                is LessonDetailState.Failure -> {
                }

                is LessonDetailState.Success -> {
                    LessonDetailScreenContent(
                        scrollState = scrollState,
                        isIconChecked = isIconChecked,
                        onIconCheckedChange = {
                            viewModel.onIconCheckedChange(it)
                        },
                        usedProducts = currentState.lesson.usedProducts,
                        item = currentState.lesson,
                        navigateToVideoPlayerScreen = navigateToVideoPlayerScreen,
                        onLessonComplete = {
                            lessonId?.let {
                                courseId?.let { it1 ->
                                    viewModel.lessonComplete(
                                        lessonId = it.toInt(),
                                        courseId = it1.toInt()
                                    )
                                }
                            }
                        }
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
fun LessonDetailScreenContent(
    scrollState: ScrollState,
    isIconChecked: Boolean,
    onIconCheckedChange: (Boolean) -> Unit,
    usedProducts: List<Product>,
    onLessonComplete: () -> Unit,
    navigateToVideoPlayerScreen: (String) -> Unit,
    item: Lesson,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        VideoImageBox(
            modifier = Modifier
                .padding(top = 5.dp)
                .clickableWithoutRipple(interactionSource) {
                    navigateToVideoPlayerScreen(item.videoSrc)
                    onLessonComplete()
                },
            item = item,
            onFavouriteChanged = { false }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            RowWithClockIcon(
                isIconChecked = isIconChecked,
                onIconCheckedChange = onIconCheckedChange,
                item = item
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = item.title,
                style = typography.l28sfD700,
                fontSize = 22.sp,
                color = Dark900
            )
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = item.description,
                style = typography.l17sfT400,
                fontSize = 16.sp,
                color = Gray800
            )
            Spacer(modifier = Modifier.height(32.dp))
            RecommendBlock(
                items = usedProducts,
                titleText = stringResource(id = R.string.product_used),
                imageExtractor = { "${Constant.IMAGE_URL}${it.imageSrc}" },
                priceExtractor = { "${it.price} ₽" },
                titleExtractor = { it.title },
                isItemInBasket = { it.basketCount > 0 },
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun RowWithClockIcon(
    isIconChecked: Boolean,
    onIconCheckedChange: (Boolean) -> Unit,
    item: Lesson,
    viewModel: LessonDetailViewModel = hiltViewModel(),
) {

    val duration = viewModel.lessonSecondToMinute(item.duration)
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = "",
            tint = Gray700
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "$duration мин",
            style = typography.l15sf700,
            fontSize = 13.sp,
            color = Gray700
        )
        Spacer(modifier = Modifier.weight(1f))
        IconToggleButton(
            checked = isIconChecked,
            onCheckedChange = onIconCheckedChange
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = "",
                tint = Brand900
            )
        }
    }
}