package com.example.drevmassapp.presentation.basket.makeOrder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.SampleTextField
import com.example.drevmassapp.common.SegmentedControl
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeOrderScreen(
    navigateBack: () -> Unit,
    viewModel: MakeOrderViewModel
) {
    val makeOrderState by viewModel.makeOrderState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    var isSuccessBottomSheetOpen by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (makeOrderState) {
            is MakeOrderState.Loading -> {
                ProgressBlock()
            }

            is MakeOrderState.Failure -> {
            }

            is MakeOrderState.Success -> {
                MakeOrderContent(
                    navigateBack = navigateBack,
                    viewModel = viewModel,
                    sheetState = sheetState,
                    successBlockIsOpen = true,
                    onDismissChange = {
                        isSuccessBottomSheetOpen = false
                    },
                    navigateToMainScreen = {}
                )
            }

            is MakeOrderState.Initial -> {
                MakeOrderContent(
                    navigateBack = navigateBack,
                    viewModel = viewModel,
                    sheetState = sheetState,
                    successBlockIsOpen = isSuccessBottomSheetOpen,
                    onDismissChange = {
                        isSuccessBottomSheetOpen = false
                    },
                    navigateToMainScreen = {}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeOrderContent(
    navigateBack: () -> Unit,
    navigateToMainScreen: () -> Unit,
    sheetState: SheetState,
    successBlockIsOpen: Boolean,
    onDismissChange: (Boolean) -> Unit,
    viewModel: MakeOrderViewModel
) {
    var selectedSegment by rememberSaveable { mutableStateOf(0) }
    var name by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var otherRecipientName by rememberSaveable { mutableStateOf("") }
    var otherRecipientPhoneNumber by rememberSaveable { mutableStateOf("") }
    var otherRecipientEmail by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = Brand900
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.make_order),
                        style = typography.l15sfT600,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(White)
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.recipient),
                style = typography.l15sf700,
                fontSize = 22.sp,
                color = Dark1000
            )
            Spacer(modifier = Modifier.height(16.dp))
            SegmentedControl(
                modifier = Modifier.fillMaxWidth(),
                useFixedWidth = true,
                itemWidth = 180.dp,
                items = listOf("Получу я", "Другой получатель"),
                onItemSelection = { selectedSegment = it }
            )

            if (selectedSegment == 0) {
                SampleTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChanged = { name = it },
                    label = stringResource(id = R.string.name),
                    value = name,
                )
                SampleTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChanged = { phoneNumber = it },
                    label = stringResource(id = R.string.phone_number),
                    value = phoneNumber,
                )
                SampleTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChanged = { email = it },
                    label = stringResource(id = R.string.email),
                    value = email,
                )
            } else {
                SampleTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChanged = { otherRecipientName = it },
                    label = stringResource(id = R.string.name),
                    value = otherRecipientName,
                )
                SampleTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChanged = { otherRecipientPhoneNumber = it },
                    label = stringResource(id = R.string.phone_number),
                    value = otherRecipientPhoneNumber,
                )
                SampleTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    onValueChanged = { otherRecipientEmail = it },
                    label = stringResource(id = R.string.email),
                    value = otherRecipientEmail,
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                text = stringResource(id = R.string.send_order),
                onButtonClick = {
                    val (recipientName, recipientPhoneNumber, recipientEmail) = if (selectedSegment == 0) {
                        Triple(name, phoneNumber, email)
                    } else {
                        Triple(otherRecipientName, otherRecipientPhoneNumber, otherRecipientEmail)
                    }
                    viewModel.makeOrder(
                        bonus = 0,
                        crmLink = "",
                        email = recipientEmail,
                        phoneNumber = recipientPhoneNumber,
                        products = emptyList(),
                        totalPrice = 1200,
                        userName = recipientName
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (successBlockIsOpen) {
                MakeOrderSuccessContent(
                    sheetState = sheetState,
                    navigateToMainScreen = navigateToMainScreen,
                    onDismissChange = onDismissChange
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeOrderSuccessContent(
    sheetState: SheetState,
    navigateToMainScreen: () -> Unit,
    onDismissChange: (Boolean) -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissChange(false) },
        containerColor = White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(White)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(112.dp),
                painter = painterResource(id = R.drawable.image_empty_basket),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.your_order_taked),
                style = typography.l28sfD700,
                color = Dark900,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.order_taked_subTitle),
                style = typography.l17sfT400,
                fontSize = 16.sp,
                color = Gray800,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                text = stringResource(id = R.string.sure),
                onButtonClick = { navigateToMainScreen() }
            )
        }
    }
}
