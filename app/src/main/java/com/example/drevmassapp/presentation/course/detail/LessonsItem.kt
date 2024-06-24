package com.example.drevmassapp.presentation.course.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.drevmassapp.R
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.common.conditional
import com.example.drevmassapp.data.model.Lesson
import com.example.drevmassapp.ui.theme.CoralGreen1000
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography

@Composable
fun LessonsItem(
    item: Lesson,
    viewModel: CourseDetailScreenViewModel = hiltViewModel(),
    onLessonDetailNavigate: (Int, String) -> Unit = { _, _ -> },
    courseId: String = "",
    isHeader: Boolean = false,
    isFavourite: () -> Boolean = { false },
    onFavouriteChanged: (Boolean) ->Unit = {},
    headerText: String = "",
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val duration = viewModel.lessonSecondToMinute(item.duration)

    Column() {
        if(isHeader){
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                text = headerText,
                style = typography.l20sfD600,
                color = Dark1000
            )
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(330.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
                .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                .clickableWithoutRipple(interactionSource) {
                    onLessonDetailNavigate(item.id, courseId)
                },
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                VideoImageBox(
                    modifier = Modifier.padding(all = 8.dp),
                    item = item,
                    isFavourite = isFavourite,
                    onFavouriteChanged = onFavouriteChanged,
                )
                RowWithLessonInfo2(
                    item = item,
                    duration = duration.toString(),
                    modifier = Modifier.padding(vertical = 8.dp),
                    isLessonCompleted = item.completed
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp),
                    text = item.title,
                    style = typography.l17sfT600,
                    color = Dark1000
                )
            }
        }
    }

}

@Composable
fun RowWithLessonInfo2(
    item: Lesson,
    duration: String,
    isLessonCompleted: Boolean,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLessonCompleted) {
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_checked_green),
                contentDescription = "",
                tint = CoralGreen1000
            )
        }
        Text(
            modifier = Modifier
                .conditional(
                    condition = isLessonCompleted,
                    modifier = { Modifier.padding(start = 5.dp) }
                ),
            text = "${item.id} урок",
            style = typography.l15sf700,
            fontSize = 13.sp,
            color = if (isLessonCompleted) CoralGreen1000 else Gray700
        )
        Icon(
            modifier = Modifier
                .size(6.dp)
                .padding(horizontal = 5.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_dot),
            contentDescription = "",
            tint = Gray800
        )
        Text(
            text = "$duration мин",
            style = typography.l15sf700,
            fontSize = 13.sp,
            color = Gray700
        )
    }
}