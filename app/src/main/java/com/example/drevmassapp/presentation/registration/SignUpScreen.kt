package com.example.drevmassapp.presentation.registration

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.MyTextField
import com.example.drevmassapp.common.PasswordTextField
import com.example.drevmassapp.common.SnackbarBlock
import com.example.drevmassapp.common.rememberImeState
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.ErrorStateColor
import com.example.drevmassapp.ui.theme.typography
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navigateToLogin: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: SingUpViewModel
) {

    val signUpState by viewModel.signUpState.collectAsStateWithLifecycle()
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()


    val isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                    text = "Неверные данные проверте еще раз",
                    iconId = R.drawable.ic_info,
                    backgroundColor = ErrorStateColor
                )
            }
        },
    ) { paddingValues ->
        when (signUpState) {
            is SignUpState.Loading -> {
                InitialBlock(
                    navigateToLogin = navigateToLogin,
                    viewModel = viewModel,
                    isLoading = true,
                    onNameValueChanged = { newValue ->
                        name = newValue
                    },
                    onEmailValueChanged = {
                        email = it
                    },
                    onPhoneNumberValueChanged = {
                        phoneNumber = it
                    },
                    onPasswordValueChanged = {
                        password = it
                    },
                    name, email, phoneNumber, password,
                    paddingValues, scrollState
                )
                Log.d("SignUpScreen", "Loading")
            }

            is SignUpState.Success -> {
                navigateToLogin()
                Log.d("SignUpScreen", "Success")
            }

            is SignUpState.Failure -> {
                isError = true
                coroutineScope.launch {
                    snackState.showSnackbar(
                        "CustomSnackbar",
                        duration = SnackbarDuration.Long
                    )
                }
                viewModel.changeState()
                Log.d("SignUpScreen", "Failure")
            }

            is SignUpState.Initial -> {
                InitialBlock(
                    navigateToLogin = navigateToLogin,
                    viewModel = viewModel,
                    isLoading = isLoading,
                    onNameValueChanged = { newValue ->
                        name = newValue
                    },
                    onEmailValueChanged = {
                        email = it
                    },
                    onPhoneNumberValueChanged = {
                        phoneNumber = it
                    },
                    onPasswordValueChanged = {
                        password = it
                    },
                    name, email, phoneNumber, password, paddingValues, scrollState
                )
                Log.d("SignUpScreen", "Initial")
            }
        }
    }
}

@Composable
fun InitialBlock(
    navigateToLogin: () -> Unit,
    viewModel: SingUpViewModel,
    isLoading: Boolean,
    onNameValueChanged: (String) -> Unit,
    onEmailValueChanged: (String) -> Unit,
    onPhoneNumberValueChanged: (String) -> Unit,
    onPasswordValueChanged: (String) -> Unit,
    name: String,
    email: String,
    phoneNumber: String,
    password: String,
    paddingValues: PaddingValues,
    scrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
            .padding(top = 12.dp, start = 32.dp, end = 32.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.registration),
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
            onValueChanged = { newValue ->
                onNameValueChanged(newValue)
            },
            hint = stringResource(id = R.string.your_name),
            leadingIcon = R.drawable.ic_profile,
            showTrailingIcon = true,
            value = name
        )
        MyTextField(
            modifier = Modifier.padding(bottom = 12.dp),
            onValueChanged = { newValue ->
                onEmailValueChanged(newValue)
            },
            hint = stringResource(id = R.string.email),
            leadingIcon = R.drawable.ic_message,
            value = email
        )
        MyTextField(
            modifier = Modifier.padding(bottom = 12.dp),
            onValueChanged = { newValue ->
                onPhoneNumberValueChanged(newValue)
            },
            hint = stringResource(id = R.string.phone_number),
            leadingIcon = R.drawable.ic_phone,
            keyboardType = KeyboardType.Number,
            value = phoneNumber
        )
        PasswordTextField(
            modifier = Modifier.padding(bottom = 12.dp),
            onValueChanged = { newValue ->
                onPasswordValueChanged(newValue)
            },
            leadingIcon = R.drawable.ic_lock,
            hint = stringResource(id = R.string.new_password),
            value = password
        )

        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            isLoading = isLoading,
            text = stringResource(id = R.string.continue1),
            enabled = (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && phoneNumber.isNotEmpty()),
            onButtonClick = { viewModel.signUp("string", email, name, password, phoneNumber) },
            backgroundColor = if (name.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && password.isNotEmpty()) {
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
                text = stringResource(id = R.string.already_have_account),
                style = typography.l15sfT600
            )
            Text(
                modifier = Modifier.clickable {
                    navigateToLogin()
                },
                text = buildAnnotatedString {
                    pushStyle(SpanStyle(color = Brand900))
                    append(" ${stringResource(id = R.string.login)}")
                    pop()
                },
                fontSize = 14.sp,
                style = typography.l15sfT600,
            )
        }
        Spacer(modifier = Modifier.height(25.dp))

        Log.d("SignUpScreen", "name :$name")
    }
}
