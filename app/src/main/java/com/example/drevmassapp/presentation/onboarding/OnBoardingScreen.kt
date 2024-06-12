package com.example.drevmassapp.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.typography
import kotlin.math.max
import kotlin.math.min

@Composable
fun OnBoardingScreen(
    navigateToRegistration: () -> Unit,
    navigateToLogin: () -> Unit,
) {

    val stepTime = 10_000

    val backgroundImages = remember {
        listOf(
            R.drawable.img_onboarding1,
            R.drawable.img_onboarding2,
            R.drawable.img_onboarding3
        )
    }

    val title = remember {
        listOf(
            R.string.title_first,
            R.string.title_second,
            R.string.title_third
        )
    }

    val description = remember {
        listOf(
            R.string.description_first,
            R.string.description_second,
            R.string.description_third
        )
    }


    val stepCount = title.size
    val currentStep = remember { mutableIntStateOf(0) }
    val isPaused = remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 25.dp)
    ) {
        Image(
            painter = painterResource(id = backgroundImages[currentStep.value]),
            contentDescription = "Фоновое изображение",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.875f)
                .background(Color.Transparent, RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset ->
                            currentStep.value = when {
                                offset.x < constraints.maxWidth / 2 -> max(
                                    0,
                                    currentStep.value - 1
                                )

                                currentStep.value == 2 -> {
                                    navigateToRegistration()
                                    currentStep.value
                                }

                                else -> {
                                    val nextStep = min(stepCount - 1, currentStep.value + 1)
                                    currentStep.value = nextStep
                                    isPaused.value = false
                                    nextStep
                                }
                            }


                        },
                        onPress = {
                            try {
                                isPaused.value = true
                                awaitRelease()
                            } finally {
                                isPaused.value = false
                            }
                        }
                    )
                }
                .padding(top = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = title[currentStep.value]),
                style = typography.l28sfD700
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = description[currentStep.value]),
                style = typography.l17sfT400,
            )
        }


        InstagramProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 13.dp, end = 13.dp),
            stepCount = stepCount,
            stepDuration = stepTime,
            unSelectedColor = Color.LightGray,
            selectedColor = Color.White,
            currentStep = currentStep.value,
            onStepChanged = { currentStep.value = it },
            isPaused = isPaused.value,
            onComplete = { navigateToRegistration() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .weight(1f),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomButton(
                    modifier = Modifier
                        .width(165.dp)
                        .height(56.dp),
                    text = stringResource(id = R.string.enter),
                    onButtonClick = {
                        navigateToLogin()
                    },
                    borderColor = Color.Transparent
                )

                CustomButton(
                    modifier = Modifier
                        .width(165.dp)
                        .height(56.dp),
                    text = stringResource(id = R.string.registration),
                    textColor = Brand900,
                    onButtonClick = {
                        navigateToRegistration()
                        val nextStep = min(stepCount - 1, currentStep.value + 1)
                        currentStep.value = nextStep
                        isPaused.value = false
                    },
                    backgroundColor = Color.White
                )
            }
        }
    }
}
