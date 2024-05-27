package com.example.drevmassapp.presentation.profileScreen.promocode

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CopyTextToClipboard
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.DashedLine
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.SetEdgeToEdge
import com.example.drevmassapp.common.SnackbarBlock
import com.example.drevmassapp.data.model.Promocode
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.SuccessStateColor
import com.example.drevmassapp.ui.theme.typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PromocodeScreen(
    navigateBack: () -> Unit,
    viewModel: PromocodeScreenViewModel
) {

    SetEdgeToEdge(lightColor = Color.White, darkColor =  Color.White)
    val promocodeState = viewModel.promocodeState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var isCopied by rememberSaveable { mutableStateOf(false) }

    val contextActivity = LocalContext.current as ComponentActivity

    LaunchedEffect(isCopied) {
        if (isCopied) {
            contextActivity.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    SuccessStateColor.toArgb(),
                    SuccessStateColor.toArgb()
                )
            )
            delay(10000L)
            contextActivity.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(Color.White.toArgb(), Color.White.toArgb())
            )
        }
    }

    when (val currentState = promocodeState.value) {
        is PromocodeState.Loading -> {
            ProgressBlock()
        }

        is PromocodeState.Success -> {
            PromocodeScreenContent(
                navigateBack = navigateBack,
                promocode = currentState.promocode,
                context = context,
                isCopied = isCopied,
                snackState = snackState,
                viewModel = viewModel,
                onCopyClick = {
                    isCopied = true
                    coroutineScope.launch {
                        snackState.showSnackbar(
                            "CustomSnackbar",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            )
        }

        is PromocodeState.Failure -> {

        }

        else -> {}
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromocodeScreenContent(
    navigateBack: () -> Unit,
    promocode: Promocode,
    viewModel: PromocodeScreenViewModel,
    context: Context,
    snackState: SnackbarHostState,
    onCopyClick: (Boolean) -> Unit,
    isCopied: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        androidx.compose.material.Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "icon back",
                            tint = Brand900
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.promocodes),
                        style = typography.l15sfT600,
                        fontSize = 17.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_info_24),
                            contentDescription = "icon back",
                            tint = Brand900
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        snackbarHost = {
            if (isCopied) {
                SnackbarBlock(
                    snackState = snackState,
                    text = "Промокод успешно скопирован",
                    iconId = R.drawable.ic_checked,
                    backgroundColor = SuccessStateColor
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(Color.White)
                .padding(16.dp)
        ) {
            if (promocode.promocode.isBlank()) {
                PromocodeEmptyScreenContent()
            } else {
                PromocodeBlockBox(
                    promocode = promocode,
                    context = context,
                    viewModel = viewModel,
                    onCopyClick = onCopyClick
                )
                Spacer(modifier = Modifier.height(12.dp))
                InfoBox()
                Spacer(modifier = Modifier.weight(1f))
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    text = stringResource(id = R.string.i_have_promocode),
                    onButtonClick = { }
                )
            }
        }
    }
}

@Composable
fun PromocodeEmptyScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
            Image(
                modifier = Modifier.size(112.dp),
                painter = painterResource(id = R.drawable.ill_promocode_112),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.not_promocode),
                style = typography.l15sfT600,
                fontSize = 17.sp,
                color = Dark1000
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.not_promocode_subTitle),
                style = typography.l17sfT400,
                fontSize = 16.sp,
                color = Gray700,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(id = R.string.i_have_promocode),
            onButtonClick = { }
        )
    }
}

@Composable
fun PromocodeBlockBox(
    promocode: Promocode,
    context: Context,
    onCopyClick: (Boolean) -> Unit,
    viewModel: PromocodeScreenViewModel
) {
    var textToCopy by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF765657), RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(all = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.promocode_for_friends),
                style = typography.l20sfD600,
                color = Color.White
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = stringResource(id = R.string.promocode_for_friends_subTitle),
                style = typography.l17sfT400,
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                text = promocode.promocode,
                style = typography.l28sfD700,
                fontSize = 22.sp,
                color = if (promocode.used == promocode.allAttempt) Color.White.copy(alpha = 0.6f)
                else Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            DashedLine(
                height = 2.dp,
                backgroundColor = if (promocode.used == promocode.allAttempt) Color.White.copy(alpha = 0.6f)
                else Color.White,
                shape = 15.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconWithText(
                    modifier = Modifier.weight(0.45f),
                    iconId = R.drawable.ic_share,
                    text = stringResource(id = R.string.share),
                    onIconClick = {
                        viewModel.shareText(context, promocode.promocode)
                    },
                    iconTint = if (promocode.used == promocode.allAttempt) Color.White.copy(alpha = 0.6f)
                    else Color.White,
                    textColor = if (promocode.used == promocode.allAttempt) Color.White.copy(alpha = 0.6f)
                    else Color.White
                )
                Spacer(modifier = Modifier.weight(0.1f))
                IconWithText(
                    modifier = Modifier.weight(0.45f),
                    iconId = R.drawable.ic_copy,
                    text = stringResource(id = R.string.take_copy),
                    onIconClick = {
                        textToCopy = promocode.promocode
                        onCopyClick(true)
                    },
                    iconTint = if (promocode.used == promocode.allAttempt) Color.White.copy(alpha = 0.6f)
                    else Color.White,
                    textColor = if (promocode.used == promocode.allAttempt) Color.White.copy(alpha = 0.6f)
                    else Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            SharedInfoBox(promocode = promocode)
        }
    }
    textToCopy?.let {
        CopyTextToClipboard(it)
        textToCopy = null
    }
}

@Composable
fun SharedInfoBox(promocode: Promocode) {

    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White, fontSize = 17.sp)) {
            append(promocode.used.toString())
        }
        withStyle(
            style = SpanStyle(
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 17.sp,
            )
        ) {
            append("/${promocode.allAttempt}")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.you_shared),
                style = typography.l17sfT400,
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.94f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(text = annotatedText)
        }
    }
}

@Composable
fun InfoBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(Brand400, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.box_promocode_info_text),
                style = typography.l17sfT400,
                fontSize = 13.sp,
                color = Gray800
            )
        }
    }
}

@Composable
fun IconWithText(
    iconId: Int,
    text: String,
    onIconClick: () -> Unit,
    iconTint: Color = Color.White,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier
                .clickable { onIconClick() }
                .size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = iconTint
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = text,
            style = typography.l15sfT600,
            color = textColor
        )
    }
}