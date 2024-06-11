package com.example.drevmassapp.presentation.course

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.SetEdgeToEdge
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.common.shimmerEffect
import com.example.drevmassapp.data.model.CourseDtotem
import com.example.drevmassapp.navigation.MainDestinations
import com.example.drevmassapp.presentation.basket.HeaderScrollingContent
import com.example.drevmassapp.presentation.basket.TransformingTopBar
import com.example.drevmassapp.presentation.course.bookMark.BookMarkScreen
import com.example.drevmassapp.presentation.profileScreen.OneProfileBlockItem
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand500
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.CoralRed1000
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography
import com.example.drevmassapp.util.Constant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CourseScreen(
    viewModel: CourseViewModel = hiltViewModel(),
    onCourseDetailsNavigate: (Int) -> Unit,
) {
    val courseState = viewModel.courseState.collectAsStateWithLifecycle()
    val currentState = courseState.value

    val scrollState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0
        }
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainDestinations.MainScreen_route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = MainDestinations.MainScreen_route) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    item {
                        HeaderScrollingContent(
                            onIconClick = { },
                            titleText = stringResource(id = R.string.course),
                            titleIconId = R.drawable.ic_bookmark
                        )
                    }
                    item {
                        CourseScreenState(
                            currentState = currentState,
                            interactionSource = interactionSource,
                            viewModel = viewModel,
                            modifier = Modifier
                                .offset(y = (-12).dp),
                            onCourseDetailsNavigate = onCourseDetailsNavigate,
                            onBookMarkNavigate = {
                                navController.navigate(MainDestinations.BookMarkScreen_route)
                            }
                        )
                    }
                }

                TransformingTopBar(
                    titleText = stringResource(id = R.string.course),
                    titleIconId = R.drawable.ic_bookmark,
                    isVisible = isScrolled,
                    onIconClick = {}
                )
            }
        }

        composable(route = MainDestinations.BookMarkScreen_route) {
            BookMarkScreen(navigateBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun CourseScreenState(
    currentState: CourseState,
    interactionSource: MutableInteractionSource,
    viewModel: CourseViewModel,
    onCourseDetailsNavigate: (Int) -> Unit,
    onBookMarkNavigate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(16.dp)
    ) {
        when (currentState) {
            is CourseState.Loading -> {
                LoadingShimmerContent()
            }

            is CourseState.Success -> {
                SuccessCourseContent(
                    interactionSource = interactionSource,
                    onBookMarkOpen = onBookMarkNavigate,
                    listCourse = currentState.course,
                    viewModel = viewModel,
                    onCourseDetailsNavigate = onCourseDetailsNavigate
                )
            }

            is CourseState.Failure -> {
                SetEdgeToEdge(lightColor = CoralRed1000, darkColor = CoralRed1000)
            }

            else -> {}
        }
    }
}


@Composable
fun SuccessCourseContent(
    interactionSource: MutableInteractionSource,
    listCourse: List<CourseDtotem>,
    onBookMarkOpen: () -> Unit,
    onCourseDetailsNavigate: (Int) -> Unit,
    viewModel: CourseViewModel
) {
    Column() {
        BookMarkBox(
            interactionSource = interactionSource,
            onBookMarkOpen = onBookMarkOpen
        )
        Spacer(modifier = Modifier.height(24.dp))
        CourseScreenBonusBox()
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier.height(480.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = listCourse) {
                CourseItem(
                    interactionSource = interactionSource,
                    item = it,
                    viewModel = viewModel,
                    onCourseItemClick = {
                        onCourseDetailsNavigate(it.id)
                    }
                )
            }
        }
    }
}

@Composable
fun BookMarkBox(
    interactionSource: MutableInteractionSource,
    onBookMarkOpen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(2.dp, borderColor, RoundedCornerShape(20.dp))
            .clickableWithoutRipple(interactionSource) {
                onBookMarkOpen()
            },
    ) {
        OneProfileBlockItem(
            modifier = Modifier.padding(all = 16.dp),
            iconId = R.drawable.ic_promocode,
            text = stringResource(id = R.string.my_bookMarks)
        )
    }
}

@Composable
fun CourseScreenBonusBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(Color.White, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp)),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.main_screen_bonus_image),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = stringResource(id = R.string.course_bonus_title),
                color = Dark900,
                style = typography.l15sfT600,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.course_bonus_subTitle),
                color = Gray800,
                style = typography.l17sfT400,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun CourseItem(
    interactionSource: MutableInteractionSource,
    item: CourseDtotem,
    onCourseItemClick: () -> Unit,
    viewModel: CourseViewModel
) {
    val duration = viewModel.lessonSecondToMinute(item.duration)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, RoundedCornerShape(24.dp))
            .border(2.dp, borderColor, RoundedCornerShape(24.dp))
            .clickableWithoutRipple(interactionSource) {
                onCourseItemClick()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .width(96.dp)
                    .height(108.dp)
                    .background(Color.White, RoundedCornerShape(15.dp))
                    .clip(RoundedCornerShape(15.dp)),
                model = "${Constant.IMAGE_URL}${item.imageSrc}",
                contentScale = ContentScale.Crop,
                contentDescription = "item image",
                loading = {}
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${item.lessonCont} уроков",
                        style = typography.l17sfT400,
                        fontSize = 13.sp,
                        color = Gray700
                    )
                    Image(
                        modifier = Modifier
                            .size(6.dp)
                            .padding(horizontal = 5.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.ic_dot),
                        contentDescription = ""
                    )
                    Text(
                        text = "$duration мин",
                        style = typography.l17sfT400,
                        fontSize = 13.sp,
                        color = Gray700
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    BonusPriceBox(item.bonusInfo.price)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = item.name, style = typography.l17sfT600, color = Dark1000)
            }
        }
    }
}

@Composable
fun BonusPriceBox(
    price: Int,
    backgroundColor: Color = Brand400,
) {
    Box(
        modifier = Modifier
            .width(76.dp)
            .height(24.dp)
            .background(backgroundColor, RoundedCornerShape(39.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "+$price",
                style = typography.l15sf700,
                fontSize = 13.sp,
                color = Dark1000
            )
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 5.dp),
                painter = painterResource(id = R.drawable.ic_bonus),
                contentDescription = "",
                tint = Brand900
            )
        }
    }
}

@Composable
fun LoadingShimmerContent() {
    Column() {
        BookMarkLoadingBox()
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(vertical = 24.dp)
                .background(Brand400, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))

        )
        repeat(3) {
            CourseItemShimmer()
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun BookMarkLoadingBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(2.dp, borderColor, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(24.dp)
                    .shimmerEffect()
            )
            Spacer(
                modifier = Modifier
                    .width(150.dp)
                    .height(16.dp)
                    .padding(start = 12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource(id = R.drawable.ic_right_12),
                contentDescription = "",
                tint = Brand400
            )
        }
    }
}

@Composable
fun CourseItemShimmer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, RoundedCornerShape(24.dp))
            .border(2.dp, borderColor, RoundedCornerShape(24.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(124.dp)
                .padding(8.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .width(96.dp)
                    .height(108.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column() {
                Row(
                    modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .width(56.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(3.dp)
                            .clip(CircleShape)
                            .background(Brand500)
                            .shimmerEffect()
                    )
                    Spacer(
                        modifier = Modifier
                            .width(56.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(170.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(90.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}
