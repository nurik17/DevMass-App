package com.example.drevmassapp.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.MyTextField
import com.example.drevmassapp.common.PasswordTextField
import com.example.drevmassapp.common.rememberImeState
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navigateToRegistration: () -> Unit,
    navigateBack: () -> Unit,
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState){
        if(imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
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
                onValueChanged = { newValue ->
                    email = newValue
                },
                hint = stringResource(id = R.string.email),
                leadingIcon = R.drawable.ic_message
            )
            PasswordTextField(
                modifier = Modifier.padding(bottom = 12.dp),
                onValueChanged = { newValue ->
                    password = newValue
                },
                leadingIcon = R.drawable.ic_lock,
                hint = stringResource(id = R.string.new_password),
            )
            Text(
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
                text = stringResource(id = R.string.continue1),
                onClick = { },
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
        }
    }
}