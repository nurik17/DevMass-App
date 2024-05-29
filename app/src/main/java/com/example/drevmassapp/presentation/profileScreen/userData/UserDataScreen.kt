package com.example.drevmassapp.presentation.profileScreen.userData

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.EditTextField
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.SetEdgeToEdge
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataScreen(
    navigateBack: () -> Unit,
    viewModel: UserDataViewModel = hiltViewModel(),
) {
    SetEdgeToEdge(lightColor = Color.White, darkColor = Color.White)


    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val userName by viewModel.userName.observeAsState()
    val phoneNumber by viewModel.userPhone.observeAsState()
    val email by viewModel.userEmail.observeAsState()
    var birthDate by remember { mutableStateOf("") }

    val isError by remember { mutableStateOf(false) }

    if (isLoading) {
        ProgressBlock()
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navigateBack() }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "icon back",
                                tint = Brand900
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.my_data),
                            style = typography.l15sfT600,
                            fontSize = 17.sp,
                            color = Color.Black,
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                userName?.let {
                    EditTextField(
                        onValueChanged = {
                            viewModel.setName(it)
                        },
                        label = stringResource(id = R.string.name),
                        value = it,
                        showTrailingIcon = true,
                        isError = isError
                    )
                }

                phoneNumber?.let {
                    EditTextField(
                        modifier = Modifier.padding(top = 16.dp),
                        onValueChanged = {
                            phoneNumber?.let { it1 -> viewModel.setPhone(it1) }
                        },
                        label = stringResource(id = R.string.phone_number),
                        value = it,
                        showTrailingIcon = true,
                        isError = isError
                    )
                }
                email?.let {
                    EditTextField(
                        modifier = Modifier.padding(top = 16.dp),
                        onValueChanged = {
                            email?.let { it1 -> viewModel.setEmail(it1) }
                        },
                        label = stringResource(id = R.string.email),
                        value = it,
                        showTrailingIcon = true,
                        isError = isError
                    )
                }
                EditTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChanged = {
                        birthDate = it
                    },
                    label = stringResource(id = R.string.birth_date),
                    value = birthDate,
                    showTrailingIcon = true,
                    isError = isError
                )
            }
        }
    }
}
