@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drevmassapp.presentation.login

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.EditTextField
import com.example.drevmassapp.common.MyTextField
import com.example.drevmassapp.common.PasswordTextField
import com.example.drevmassapp.common.SnackbarBlock
import com.example.drevmassapp.common.rememberImeState
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark650
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.ErrorStateColor
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.typography
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateToRegistration: () -> Unit,
    navigateBack: () -> Unit,
    navigateToMain:() ->Unit,
    viewModel: LoginViewModel
) {

    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var emailForgot by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = imeState) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    LaunchedEffect(key1 = isError) {
        if (isError) {
            systemUiController.setStatusBarColor(ErrorStateColor)
            delay(10000)
            systemUiController.setStatusBarColor(Color.White)
            isError = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
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
            if (isError) {
                SnackbarBlock(
                    snackState = snackState,
                    text = "Неверный логин или пароль",
                    iconId = R.drawable.ic_info,
                    backgroundColor = ErrorStateColor
                )
            }
        }
    ) { paddingValues ->
        when (loginState) {
            is LoginState.Loading -> {
                isLoading = true
                LoginScreenContent(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    scrollState = scrollState,
                    isLoading = true,
                    isError = isError,
                    sheetState = sheetState,
                    email = email,
                    password = password,
                    isSheetOpen = isSheetOpen,
                    onSheetStateChange = {
                        isSheetOpen = it
                    },
                )
                Log.d("LoginScreen", "login: loading")
            }

            is LoginState.Success -> {
                isLoading = false
                navigateToMain()
                Log.d("LoginScreen", "login: success")
            }

            is LoginState.Failure -> {
                isError = true
                isLoading = false
                coroutineScope.launch {
                    snackState.showSnackbar(
                        "CustomSnackbar",
                        duration = SnackbarDuration.Long
                    )
                }
                viewModel.changeState()
                Log.d("LoginScreen", "login: failure")
            }

            is LoginState.Initial -> {
                LoginScreenContent(
                    viewModel = viewModel,
                    navigateToRegistration = navigateToRegistration,
                    paddingValues = paddingValues,
                    scrollState = scrollState,
                    email = email,
                    password = password,
                    onEmailValueChanged = {
                        email = it
                    },
                    onPasswordValueChanged = {
                        password = it
                    },
                    isLoading = isLoading,
                    isError = isError,
                    sheetState = sheetState,
                    isSheetOpen = isSheetOpen,
                    onSheetStateChange = {
                        isSheetOpen = it
                    },
                    emailForgot = emailForgot,
                    onForgotEmailValueChanged = {
                        emailForgot = it
                    }
                )
                Log.d("LoginScreen", "login: initial")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    navigateToRegistration: () -> Unit = {},
    paddingValues: PaddingValues,
    scrollState: ScrollState,
    email: String = "",
    emailForgot: String = "",
    password: String = "",
    onEmailValueChanged: (String) -> Unit = {},
    onForgotEmailValueChanged: (String) -> Unit = {},
    onPasswordValueChanged: (String) -> Unit = {},
    isLoading: Boolean = false,
    isError: Boolean = false,
    sheetState: SheetState,
    onSheetStateChange: (Boolean) -> Unit = {},
    isSheetOpen: Boolean = false,
    viewModel: LoginViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues = paddingValues)
            .padding(top = 12.dp, start = 32.dp, end = 32.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.come_back),
            style = typography.l28sfD700
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.registration_description),
            style = typography.l17sfT400,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        MyTextField(
            modifier = Modifier.padding(bottom = 12.dp),
            onValueChanged = {
                onEmailValueChanged(it)
            },
            hint = stringResource(id = R.string.email),
            leadingIcon = R.drawable.ic_message,
            value = email,
            isError = isError,
            showTrailingIcon = true
        )
        PasswordTextField(
            modifier = Modifier.padding(bottom = 12.dp),
            onValueChanged = {
                onPasswordValueChanged(it)
            },
            leadingIcon = R.drawable.ic_lock,
            hint = stringResource(id = R.string.password),
            value = password,
            isError = isError
        )

        Text(
            modifier = Modifier.clickable {
                onSheetStateChange(true)
            },
            text = stringResource(id = R.string.forgot_password),
            style = typography.l15sfT600,
            color = Brand900
        )

        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .imePadding(),
            isLoading = isLoading,
            text = stringResource(id = R.string.continue1),
            enabled = (email.isNotEmpty() && password.isNotEmpty()),
            onButtonClick = {
                viewModel.login("string", email, password)
            },
            backgroundColor = if (email.isNotEmpty() && password.isNotEmpty()) {
                Brand900
            } else {
                Color(0xFFD3C8B3)
            },
            borderColor = Color.Transparent
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.still_dont_have_account),
                style = typography.l15sfT600
            )
            Text(
                modifier = Modifier.clickable {
                    navigateToRegistration()
                },
                text = buildAnnotatedString {
                    pushStyle(SpanStyle(color = Brand900))
                    append(" ${stringResource(id = R.string.registration)}")
                    pop()
                },
                fontSize = 14.sp,
                style = typography.l15sfT600,
            )
        }
        Spacer(modifier = Modifier.height(25.dp))

        if (isSheetOpen) {
            ResetPasswordContent(
                sheetState = sheetState,
                onSheetStateChange = {
                    onSheetStateChange(false)
                },
                onEmailValueChanged = {
                    onForgotEmailValueChanged(it)
                },
                emailForgot = emailForgot,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ResetPasswordContent(
    sheetState: SheetState,
    onSheetStateChange: (Boolean) -> Unit,
    emailForgot: String,
    onEmailValueChanged: (String) -> Unit,
    viewModel: LoginViewModel,
) {
    val forgotPasswordState by viewModel.forgotPasswordState.collectAsStateWithLifecycle()

    val successBottomSheetState = rememberModalBottomSheetState()
    var isSuccessBottomSheetOpen by rememberSaveable { mutableStateOf(false) }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    when (forgotPasswordState) {
        is ForgotPasswordState.Loading -> {
            isLoading = true
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { onSheetStateChange(false) },
                containerColor = Color.White,
                dragHandle = { BottomSheetDefaults.DragHandle() },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "icon back",
                            tint = Brand900
                        )
                        Text(
                            text = stringResource(id = R.string.reset_password),
                            style = typography.l15sfT600,
                            fontSize = 17.sp,
                        )
                        Box {}
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = stringResource(id = R.string.reset_password_info_text),
                        style = typography.l17sfT400,
                        color = Gray800,
                        fontSize = 15.sp
                    )

                    EditTextField(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        onValueChanged = {
                            onEmailValueChanged(it)
                        },
                        label = stringResource(id = R.string.email),
                        value = emailForgot,
                        showTrailingIcon = true,
                        isError = isError
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    CustomButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .imePadding(),
                        isLoading = true,
                        text = stringResource(id = R.string.reset_password),
                        enabled = (emailForgot.isNotEmpty()),
                        onButtonClick = {
                            val decodedEmail = URLDecoder.decode(emailForgot, "UTF-8")
                            viewModel.forgotPassword(decodedEmail)
                            Log.d("ResetPasswordContent", decodedEmail)
                        },
                        backgroundColor = if (emailForgot.isNotEmpty()) {
                            Brand900
                        } else {
                            Color(0xFFD3C8B3)
                        },
                        borderColor = Color.Transparent
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    if (isSuccessBottomSheetOpen) {
                        SuccessBottomContent(
                            sheetState = successBottomSheetState,
                            onDismissChange = {
                                isSuccessBottomSheetOpen = false
                            },
                            emailForgot = emailForgot,
                            successBottomButtonClick = {

                            }
                        )
                    }
                }
            }
            Log.d("LoginScreen", "password: loading")
        }

        is ForgotPasswordState.Success -> {
            ForgotPasswordBottomContent(
                sheetState = sheetState,
                successBottomSheetState = successBottomSheetState,
                onSheetStateChange = onSheetStateChange,
                onEmailValueChanged = onEmailValueChanged,
                emailForgot = emailForgot,
                isError = isError,
                isLoading = isLoading,
                viewModel = viewModel,
                isSuccessBottomSheetOpen = true,
                onDismissChange = {
                    isSuccessBottomSheetOpen = false
                },
                successBottomButtonClick = {
                    onSheetStateChange(false)
                    isSuccessBottomSheetOpen = false
                }
            )
            Log.d("LoginScreen", "password success")
        }

        is ForgotPasswordState.Failure -> {
            isError = true
            isLoading = false
            viewModel.changeStateForgotPassword()
            Log.d("LoginScreen", "password failure")
        }

        is ForgotPasswordState.Initial -> {
            ForgotPasswordBottomContent(
                sheetState = sheetState,
                successBottomSheetState = successBottomSheetState,
                onSheetStateChange = onSheetStateChange,
                onEmailValueChanged = onEmailValueChanged,
                emailForgot = emailForgot,
                isError = isError,
                isLoading = isLoading,
                viewModel = viewModel,
                isSuccessBottomSheetOpen = isSuccessBottomSheetOpen,
                onDismissChange = {
                    isSuccessBottomSheetOpen = false
                },
                successBottomButtonClick = {
                    onSheetStateChange(false)
                    isSuccessBottomSheetOpen = false
                }
            )
            Log.d("LoginScreen", "password initial")
        }
    }
}

@Composable
fun ForgotPasswordBottomContent(
    sheetState: SheetState,
    successBottomSheetState: SheetState,
    onSheetStateChange: (Boolean) -> Unit,
    onEmailValueChanged: (String) -> Unit,
    emailForgot: String,
    isError: Boolean,
    isLoading: Boolean,
    viewModel: LoginViewModel,
    isSuccessBottomSheetOpen: Boolean,
    onDismissChange: (Boolean) -> Unit,
    successBottomButtonClick: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onSheetStateChange(false) },
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "icon back",
                    tint = Brand900
                )
                Text(
                    text = stringResource(id = R.string.reset_password),
                    style = typography.l15sfT600,
                    fontSize = 17.sp,
                )
                Box {}
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(id = R.string.reset_password_info_text),
                style = typography.l17sfT400,
                color = Gray800,
                fontSize = 15.sp
            )

            EditTextField(
                modifier = Modifier
                    .padding(top = 16.dp),
                onValueChanged = {
                    onEmailValueChanged(it)
                },
                label = stringResource(id = R.string.email),
                value = emailForgot,
                showTrailingIcon = true,
                isError = isError
            )
            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .imePadding(),
                isLoading = isLoading,
                text = stringResource(id = R.string.reset_password),
                enabled = (emailForgot.isNotEmpty()),
                onButtonClick = {
                    val decodedEmail = URLDecoder.decode(emailForgot, "UTF-8")
                    viewModel.forgotPassword(decodedEmail)
                },
                backgroundColor = if (emailForgot.isNotEmpty()) {
                    Brand900
                } else {
                    Color(0xFFD3C8B3)
                },
                borderColor = Color.Transparent
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
        if (isSuccessBottomSheetOpen) {
            SuccessBottomContent(
                sheetState = successBottomSheetState,
                onDismissChange = {
                    onDismissChange(false)
                },
                emailForgot = emailForgot,
                successBottomButtonClick = successBottomButtonClick
            )
        }
    }
}

@Composable
fun SuccessBottomContent(
    sheetState: SheetState,
    onDismissChange: (Boolean) -> Unit,
    emailForgot: String,
    successBottomButtonClick: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissChange(false) },
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.reset_password),
                style = typography.l28sfD700,
                color = Dark650,
                fontSize = 22.sp
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = buildAnnotatedString {
                    append("На почту ")
                    withStyle(style = SpanStyle(color = Dark900)) {
                        append(emailForgot)
                    }
                    append(" мы отправили инструкцию для сброса пароля.")
                },
                style = typography.l17sfT400,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                text = stringResource(id = R.string.sure),
                onButtonClick = {
                    successBottomButtonClick()
                },
                backgroundColor = Brand900,
                borderColor = Color.Transparent
            )
        }
    }
}