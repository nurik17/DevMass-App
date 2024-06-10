package com.example.drevmassapp.presentation.profileScreen.userData

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.drevmassapp.R
import com.example.drevmassapp.common.AlertDialogComponent
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.EditTextField
import com.example.drevmassapp.common.HintTextField
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.SegmentedControl
import com.example.drevmassapp.common.SetEdgeToEdge
import com.example.drevmassapp.common.SnackbarBlock
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.CoralRed1000
import com.example.drevmassapp.ui.theme.ErrorStateColor
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.typography
import kotlinx.coroutines.launch


@Composable
fun UserDataScreen(
    navigateBack: () -> Unit,
    navigateOnBoarding: () -> Unit,
    navController: NavHostController,
    viewModel: UserDataViewModel = hiltViewModel(),
) {
    SetEdgeToEdge(lightColor = Color.White, darkColor = Color.White)

    val userDataState = viewModel.updateUserDataState.collectAsStateWithLifecycle()
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val currentState = userDataState.value
    val isLoadingDefaultState by viewModel.isLoading.collectAsStateWithLifecycle()
    val isErrorInput by remember { mutableStateOf(false) }
    val isErrorState by remember { mutableStateOf(false) }


    when (currentState) {
        is UserDataState.Loading -> {
            ProgressBlock()
            Log.d("UserDataScreen", "loading")
        }

        is UserDataState.Failure -> {
            val errorMessage = currentState.message
            SetEdgeToEdge(lightColor = CoralRed1000, darkColor = CoralRed1000)
            coroutineScope.launch {
                snackState.showSnackbar(
                    "CustomSnackbar",
                    duration = SnackbarDuration.Short
                )
            }
            UserDataScreenContent(
                isLoadingDefaultState = false,
                isErrorInput = isErrorInput,
                isErrorState = true,
                navigateBack = navigateBack,
                snackState = snackState,
                snackBarMessage = errorMessage,
                viewModel = viewModel
            )
            Log.d("UserDataScreen", "error")

        }

        is UserDataState.Success -> {
            /*SetEdgeToEdge(
                lightColor = SuccessStateColor,
                darkColor = SuccessStateColor
            )*/
            navController.popBackStack()
            viewModel.changeState()
            Log.d("UserDataScreen", "success")
        }

        is UserDataState.Initial -> {
            UserDataScreenContent(
                isLoadingDefaultState = isLoadingDefaultState,
                isErrorInput = isErrorInput,
                isErrorState = isErrorState,
                navigateBack = navigateBack,
                snackState = snackState,
                navigateOnBoarding = navigateOnBoarding,
                viewModel = viewModel
            )
            Log.d("UserDataScreen", "initial")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataScreenContent(
    isLoadingDefaultState: Boolean,
    isErrorInput: Boolean,
    isErrorState: Boolean,
    snackBarMessage: String = "",
    navigateBack: () -> Unit,
    snackState: SnackbarHostState,
    navigateOnBoarding:() ->Unit = {},
    viewModel: UserDataViewModel,
) {
    val userName by viewModel.userName.observeAsState()
    val phoneNumber by viewModel.userPhone.observeAsState()
    val email by viewModel.userEmail.observeAsState()
    var birthDate by remember { mutableStateOf("") }

    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    var selectedSegmentSex by rememberSaveable { mutableStateOf(0) }
    var selectedSegmentActivity by rememberSaveable { mutableStateOf(0) }

    val isDialogDeleteVisibility by viewModel.isDeleteDialogVisible.collectAsStateWithLifecycle()


    val userData by viewModel.userId.observeAsState()
    val userId = userData?.id ?: 887

    if (isLoadingDefaultState) {
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
            snackbarHost = {
                if (isErrorState) {
                    SnackbarBlock(
                        snackState = snackState,
                        text = snackBarMessage,
                        iconId = R.drawable.ic_info,
                        backgroundColor = ErrorStateColor
                    )
                }else{
                    SnackbarBlock(
                        snackState = snackState,
                        text = "Сохранение прошло успешно",
                        iconId = R.drawable.ic_info,
                        backgroundColor = ErrorStateColor
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

                userName?.let {
                    EditTextField(
                        onValueChanged = {
                            viewModel.setName(it)
                        },
                        label = stringResource(id = R.string.name),
                        value = it,
                        showTrailingIcon = true,
                        isError = isErrorInput
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
                        isError = isErrorInput
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
                        isError = isErrorInput
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
                    isError = isErrorInput
                )
                Spacer(modifier = Modifier.height(16.dp))
                UserInfoContent(
                    onSelectedSegment = {
                        selectedSegmentSex = it
                    },
                    onSelectedSegmentActivity = {
                        selectedSegmentActivity = it
                        Log.d("UserDataScreenContent", it.toString())
                    },
                    onValueChangedHeight = {
                        height = it
                    },
                    onValueChangedWeight = {
                        weight = it
                    },
                    height = height,
                    weight = weight,
                    onChangeUserDataClick = {
                        viewModel.updateUserData(
                            activity = selectedSegmentActivity,
                            birth = birthDate,
                            email = email!!,
                            gender = selectedSegmentSex,
                            height = height.toInt(),
                            id = userId,
                            name = userName!!,
                            phoneNumber = phoneNumber!!,
                            weight = weight.toInt()
                        )
                    },
                    onDeleteClick = {
                        viewModel.changeVisibilityDeleteDialog(true)
                    }
                )
                if(isDialogDeleteVisibility){
                    AlertDialogComponent(
                        textTitle = stringResource(id = R.string.are_you_sure_to_log_deleteAccount),
                        textConfirm = stringResource(id = R.string.delete),
                        textDismiss = stringResource(id = R.string.stay),
                        subTitleText = stringResource(id = R.string.delete_account_subTitle),
                        onDismissRequest = { viewModel.changeVisibilityDeleteDialog(false) },
                        onClickConfirmButton = {
                            viewModel.deleteUser()
                            navigateOnBoarding()
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun UserInfoContent(
    onSelectedSegment: (Int) -> Unit,
    onSelectedSegmentActivity: (Int) -> Unit,
    onValueChangedHeight: (String) -> Unit,
    onValueChangedWeight: (String) -> Unit,
    height: String,
    weight: String,
    onChangeUserDataClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column() {
        Text(
            text = stringResource(id = R.string.sex),
            style = typography.l13sf500,
            color = Gray700
        )
        Spacer(modifier = Modifier.height(16.dp))
        SegmentedControl(
            modifier = Modifier.fillMaxWidth(),
            useFixedWidth = true,
            itemWidth = 118.dp,
            items = listOf("Не указано", "Мужской", "Женский"),
            onItemSelection = {
                onSelectedSegment(it)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            HintTextField(
                modifier = Modifier.weight(0.45f),
                onValueChanged = onValueChangedHeight,
                value = height,
                keyboardType = KeyboardType.Number,
                placeholder = stringResource(id = R.string.height)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            HintTextField(
                modifier = Modifier.weight(0.45f),
                onValueChanged = onValueChangedWeight,
                keyboardType = KeyboardType.Number,
                value = weight,
                placeholder = stringResource(id = R.string.weight)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.activity),
            style = typography.l13sf500,
            color = Gray700
        )
        Spacer(modifier = Modifier.height(16.dp))
        SegmentedControl(
            modifier = Modifier.fillMaxWidth(),
            useFixedWidth = true,
            itemWidth = 120.dp,
            items = listOf("Низкая", "Средняя", "Высокая"),
            onItemSelection = {
                onSelectedSegmentActivity(it)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.clickable {
                onDeleteClick()
            },
            text = stringResource(id = R.string.delete_account),
            style = typography.l15sfT600,
            color = CoralRed1000
        )
        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(id = R.string.save_changes),
            onButtonClick = {
                onChangeUserDataClick()
            },
            enabled = true,
            backgroundColor = if (height.isNotEmpty() && weight.isNotEmpty()) {
                Brand900//continue empty places
            } else {
                Color(0xFFD3C8B3)
            },
            borderColor = Color.Transparent,
        )
    }
}