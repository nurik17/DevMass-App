package com.example.drevmassapp.presentation.course.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.CourseDetailsDto
import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.presentation.course.BonusPriceBox
import com.example.drevmassapp.ui.theme.Brand300
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand500
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.GrayDefault
import com.example.drevmassapp.ui.theme.White60
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography
import com.example.drevmassapp.util.Constant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String?,
    navigateBack: () -> Unit,
    onLessonDetailNavigate: (Int, String) -> Unit,
    viewModel: CourseDetailScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        courseId?.let { viewModel.getCourseDetailsById(it.toInt()) }
        courseId?.let { viewModel.getLessonList(it.toInt()) }
    }

    val detailState = viewModel.courseDetailState.collectAsStateWithLifecycle()
    val currentState = detailState.value

    val isSwitchChecked by viewModel.isSwitchChecked.collectAsStateWithLifecycle()
    val isDayOfLessonVisible by viewModel.isDayOfLessonSheet.collectAsStateWithLifecycle()
    val clickedDayOfLesson by viewModel.clickedDays.collectAsStateWithLifecycle()
    val isTimePickerOpen by viewModel.isTimePickerOpen.collectAsStateWithLifecycle()
    val isCourseStarted by viewModel.isCourseStarted.collectAsStateWithLifecycle()
    val isCourseFinished by viewModel.isCourseFinished.collectAsStateWithLifecycle()
    val alertDialogOpen by viewModel.alertDialogOpen.collectAsStateWithLifecycle()

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val showTitleThreshold = with(density) { 300.dp.toPx() }
    val title = remember { mutableStateOf("") }

    val timePickerState = rememberTimePickerState()

    val dayOfLessonSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(scrollState.value) {
        if (scrollState.value > showTitleThreshold) {
            currentState.let { state ->
                if (state is CourseDetailState.Success) {
                    title.value = state.courseDetail.course.name
                }
            }
        } else {
            title.value = ""
        }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = title.value,
                        color = Color.Black,
                        style = typography.l17sfT600,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(top = 5.dp),
                        onClick = { navigateBack() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = if (scrollState.value > showTitleThreshold) Brand900 else Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.padding(top = 5.dp),
                        onClick = { }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "",
                            tint = if (scrollState.value > showTitleThreshold) Brand900 else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (scrollState.value > showTitleThreshold) Color.White else Color.Transparent,
                    scrolledContainerColor = Color.White
                ),
            )
        },
        modifier = Modifier,
    ) { padding ->
        Log.d("CourseDetailScreen", padding.toString())
        when (currentState) {
            is CourseDetailState.Loading -> {
                ProgressBlock()
            }

            is CourseDetailState.Failure -> {
            }

            is CourseDetailState.Success -> {
                courseId?.let {
                    SuccessCourseDetailContent(
                        item = currentState.courseDetail,
                        viewModel = viewModel,
                        interactionSource = interactionSource,
                        scrollState = scrollState,
                        isSwitchChecked = isSwitchChecked,
                        onCheckedChange = { icChecked ->
                            courseId.let { viewModel.onCheckedChange(it.toInt(), icChecked) }
                        },
                        onDayClick = {
                            viewModel.setBottomSheetState(true)
                        },
                        onTimeClick = {
                            viewModel.setTimePickerState(true)
                        },
                        isDayOfLessonVisible = isDayOfLessonVisible,
                        sheetState = dayOfLessonSheetState,
                        onDismissChange = {
                            viewModel.setBottomSheetState(false)
                        },
                        courseId = it.toInt(),
                        clickedDays = clickedDayOfLesson,
                        showTimePicker = isTimePickerOpen,
                        onTimePickerChange = {
                            viewModel.setTimePickerState(false)
                        },
                        timePickerState = timePickerState,
                        onLessonDetailNavigate = onLessonDetailNavigate,
                        isCourseStarted = isCourseStarted,
                        onAlertDialogDismissChange = {
                            viewModel.onAlertDialogChange(false)
                        },
                        showFinishDialog = isCourseFinished,
                        isCourseFinished = isCourseStarted
                    )
                }
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessCourseDetailContent(
    onCheckedChange: (Boolean) -> Unit,
    item: CourseDetailsDto,
    interactionSource: MutableInteractionSource,
    scrollState: ScrollState,
    isSwitchChecked: Boolean,
    showTimePicker: Boolean,
    onDayClick: () -> Unit,
    sheetState: SheetState,
    timePickerState: TimePickerState,
    onTimeClick: () -> Unit,
    onDismissChange: (Boolean) -> Unit,
    onAlertDialogDismissChange: (Boolean) -> Unit,
    onTimePickerChange: (Boolean) -> Unit,
    isDayOfLessonVisible: Boolean,
    clickedDays: Map<Int, List<String>>,
    courseId: Int,
    isCourseStarted: Boolean,
    isCourseFinished: Boolean,
    onLessonDetailNavigate: (Int, String) -> Unit,
    showFinishDialog: Boolean,
    viewModel: CourseDetailScreenViewModel
) {
    val duration = viewModel.lessonSecondToMinute(item.course.duration)
    val days =
        listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье")

    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    var timerText by remember {
        mutableStateOf(viewModel.getTimerText(courseId))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        ImageInfoBox(
            duration = duration.toString(),
            item = item,
            isCourseStarted = isCourseStarted,
            onStartCourseClick = {
                viewModel.startCourse(courseId)
                viewModel.getCourseDetailsById(courseId)
            }
        )

        WhiteRoundedBlockBox(
            interactionSource = interactionSource,
            item = item,
            isChecked = isSwitchChecked,
            onCheckedChange = onCheckedChange,
            lessonList = item.course.lessons,
            viewModel = viewModel,
            onDayClick = onDayClick,
            onTimeClick = onTimeClick,
            courseId = courseId,
            timerText = timerText,
            onLessonDetailNavigate = onLessonDetailNavigate,
            isCourseStarted = isCourseStarted,
        )
    }
    if (isDayOfLessonVisible) {
        DayOfLessonsSheet(
            days = days,
            sheetState = sheetState,
            onDismissChange = onDismissChange,
            onSaveClickedItems = {
                viewModel.saveClickedDays()
            },
            courseId = courseId,
            clickedDays = clickedDays
        )
    }
    if (showTimePicker) {
        TimePickerDialog(
            onCancel = { onTimePickerChange(false) },
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                cal.set(Calendar.MINUTE, timePickerState.minute)
                cal.isLenient = false

                timerText = formatter.format(cal.time)
                viewModel.saveTimerText(courseId, timerText)
                onTimePickerChange(false)
            },
        ) {
            TimeInput(state = timePickerState)
        }
    }
    if (isCourseFinished && showFinishDialog) {
        BasicAlertDialog(
            modifier = Modifier
                .height(334.dp)
                .width(281.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            onDismissRequest = { onAlertDialogDismissChange(false) },
        ) {
            FinishCourseDialogContent(
                item = item,
                onAlertDialogChange = onAlertDialogDismissChange
            )
        }
    }
}


@Composable
fun ImageInfoBox(
    item: CourseDetailsDto,
    duration: String,
    isCourseStarted: Boolean,
    onStartCourseClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.46f)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = "${Constant.IMAGE_URL}${item.course.imageSrc}",
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = item.course.name,
                style = typography.l28sfD700,
                color = Color.White
            )
            RowWithLessonInfo(
                item = item,
                duration = duration,
                modifier = Modifier.padding(top = 14.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (!isCourseStarted) {
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    text = stringResource(id = R.string.start_course),
                    backgroundColor = Color.White.copy(alpha = 0.2f),
                    borderColor = Color.Transparent,
                    onButtonClick = onStartCourseClick
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun WhiteRoundedBlockBox(
    interactionSource: MutableInteractionSource,
    item: CourseDetailsDto,
    isChecked: Boolean,
    isCourseStarted: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    lessonList: List<Lesson>,
    onDayClick: () -> Unit,
    onTimeClick: () -> Unit,
    courseId: Int,
    timerText: String,
    onLessonDetailNavigate: (Int, String) -> Unit,
    viewModel: CourseDetailScreenViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationY = -70f
            }
            .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                if (isCourseStarted) {
                    CourseProgressBox(item = item)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Text(
                    text = item.course.description,
                    style = typography.l17sfT400,
                    fontSize = 16.sp,
                    color = Gray800
                )
                BonusAccuralsBox(
                    modifier = Modifier.padding(top = 24.dp),
                    item = item,
                )
                LessonCalendarBox(
                    modifier = Modifier.padding(top = 24.dp),
                    interactionSource = interactionSource,
                    isChecked = isChecked,
                    onCheckedChange = onCheckedChange,
                    onDayClick = onDayClick,
                    onTimeClick = onTimeClick,
                    courseId = courseId,
                    timerText = timerText
                )
                Text(
                    modifier = Modifier
                        .padding(top = 24.dp),
                    text = stringResource(id = R.string.lessons),
                    style = typography.l28sfD700,
                    fontSize = 22.sp,
                    color = Dark900
                )
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    lessonList.forEach { lesson ->
                        LessonsItem(
                            item = lesson,
                            viewModel = viewModel,
                            onLessonDetailNavigate = onLessonDetailNavigate,
                            courseId = courseId.toString(),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun BonusAccuralsBox(
    item: CourseDetailsDto,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(GrayDefault, RoundedCornerShape(20.dp))
    ) {
        Image(
            modifier = Modifier.padding(start = 130.dp),
            painter = painterResource(id = R.drawable.icon_tree),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.2f))
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Text(
                text = "Начислим ${item.course.bonusInfo.price} бонусов за\nпрохождение курса",
                style = typography.l15sfT600,
                color = Dark900
            )
            Spacer(modifier = Modifier.weight(1f))
            BonusPriceBox(price = item.course.bonusInfo.price, backgroundColor = White60)
        }
    }
}


@Composable
fun LessonCalendarBox(
    interactionSource: MutableInteractionSource,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDayClick: () -> Unit,
    onTimeClick: () -> Unit,
    courseId: Int,
    timerText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(2.dp, borderColor, RoundedCornerShape(20.dp))
            .clickableWithoutRipple(interactionSource) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(all = 15.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "",
                    tint = Brand900
                )
                Spacer(modifier = Modifier.width(13.dp))
                Text(
                    text = stringResource(id = R.string.lesson_calendar),
                    style = typography.l15sfT600,
                    fontSize = 17.sp,
                    color = Dark1000
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Brand900,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = GrayDefault,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }
            if (isChecked) {
                CheckedLessonCalendarBlock(
                    interactionSource = interactionSource,
                    onDayClick = onDayClick,
                    onTimeClick = onTimeClick,
                    courseId = courseId,
                    timerText = timerText
                )
            }
        }
    }
}

@Composable
fun CheckedLessonCalendarBlock(
    interactionSource: MutableInteractionSource,
    onDayClick: () -> Unit,
    onTimeClick: () -> Unit,
    courseId: Int,
    timerText: String,
    viewModel: CourseDetailScreenViewModel = hiltViewModel()
) {
    val shortText = viewModel.getShortTextForDays(courseId)
    Log.d("CourseDetailScreen", "shortText: $shortText")

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
                .clickableWithoutRipple(interactionSource) {
                    onDayClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.day_of_lessons),
                style = typography.l17sfT400, color = Dark900
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = shortText,
                style = typography.l17sfT600, color = Brand900
            )
            Icon(
                modifier = Modifier
                    .size(12.dp)
                    .padding(start = 5.dp)
                    .rotate(180f),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "",
                tint = Brand900
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .clickableWithoutRipple(interactionSource) {
                    onTimeClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.time),
                style = typography.l17sfT400, color = Dark900
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = timerText,
                style = typography.l17sfT600, color = Brand900
            )
            Icon(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .size(12.dp)
                    .rotate(180f),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "",
                tint = Brand900
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayOfLessonsSheet(
    days: List<String>,
    sheetState: SheetState,
    onDismissChange: (Boolean) -> Unit,
    onSaveClickedItems: () -> Unit,
    courseId: Int,
    clickedDays: Map<Int, List<String>>,
    viewModel: CourseDetailScreenViewModel = hiltViewModel(),
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissChange(false) },
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
                .padding(all = 24.dp)
        ) {
            Text(
                modifier = Modifier.clickable {
                    viewModel.deleteListOfDays(courseId)
                },
                text = stringResource(id = R.string.day_of_lessons),
                style = typography.l20sfD600,
                color = Dark900
            )
            LazyVerticalGrid(
                modifier = Modifier.padding(top = 24.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(days.size) { index ->
                    DayOfLessonItem(
                        text = days[index],
                        courseId = courseId,
                        clickedDays = clickedDays
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                text = stringResource(id = R.string.save),
                onButtonClick = {
                    onDismissChange(false)
                    onSaveClickedItems()
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Composable
fun VideoImageBox(
    modifier: Modifier = Modifier,
    item: Lesson
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(185.dp)
            .background(Color.Transparent, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent, RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp)),
            model = "${Constant.IMAGE_URL}${item.imageSrc}",
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        IconToggleButton(
            modifier = Modifier.align(Alignment.TopEnd),
            checked = false,
            onCheckedChange = {}
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_bookmark),
                contentDescription = "",
                tint = Color.White
            )
        }
        PlayIconBox(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun PlayIconBox(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(44.dp)
            .background(Dark1000, CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(17.dp),
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Composable
fun RowWithLessonInfo(
    item: CourseDetailsDto,
    duration: String,
    lessonTextColor: Color = Color.White,
    durationTextColor: Color = Color.White,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = "",
            tint = Color.White
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "${item.course.lessonCont} уроков",
            style = typography.l15sf700,
            fontSize = 13.sp,
            color = lessonTextColor
        )
        Image(
            modifier = Modifier
                .size(6.dp)
                .padding(horizontal = 5.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_dot),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White)
        )
        Text(
            text = "$duration мин",
            style = typography.l15sf700,
            fontSize = 13.sp,
            color = durationTextColor
        )
    }
}


@Composable
fun DayOfLessonItem(
    text: String,
    courseId: Int,
    clickedDays: Map<Int, List<String>>,
    viewModel: CourseDetailScreenViewModel = hiltViewModel()
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isClicked = clickedDays[courseId]?.contains(text) ?: false

    val borderColor = if (isClicked) Brand900 else Brand400
    val backgroundColor = if (isClicked) Brand300 else Color.White
    val textColor = if (isClicked) Brand900 else Dark1000

    Box(
        modifier = Modifier
            .width(160.dp)
            .height(52.dp)
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickableWithoutRipple(interactionSource) {
                viewModel.onDayOfLessonItemClick(courseId, text)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = typography.l15sfT600,
            color = textColor
        )
    }
}

@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = Color.White
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = onConfirm
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
fun CourseProgressBox(
    item: CourseDetailsDto,
    viewModel: CourseDetailScreenViewModel = hiltViewModel()
) {
    val progress = viewModel.calculateCourseProgress(item)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(Brand400, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.finished_lessons),
                    style = typography.l15sfT600,
                    color = Dark1000
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${item.completedLessons} из ${item.allLessons}",
                    style = typography.l15sfT600,
                    color = Dark1000,
                )
            }

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(15.dp)),
                color = Brand900,
                trackColor = Brand500,
            )
        }
    }
}


@Composable
fun FinishCourseDialogContent(
    item: CourseDetailsDto,
    onAlertDialogChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(112.dp),
            model = R.drawable.image_finish_course,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.congratulations),
            style = typography.l20sfD600,
            color = Dark900,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8 .dp))
        Text(
            text = "Вы успешно прошли курс и получаете ${item.course.bonusInfo.price} бонусов.",
            style = typography.l17sfT400,
            fontSize = 16.sp,
            color = Gray800,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            text = stringResource(id = R.string.sure),
            onButtonClick = {
                onAlertDialogChange(false)
            }
        )
    }
}