package com.example.drevmassapp.presentation.profileScreen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.AlertDialogComponent
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.SampleTextField
import com.example.drevmassapp.common.SetEdgeToEdge
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.ui.theme.Blue1000
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navigateToPoints: () -> Unit,
    onPromocodeNavigate: () -> Unit,
    onUserDataNavigate: () -> Unit,
    onNotificationNavigate: () -> Unit,
    onInformationScreenNavigate: () -> Unit,
) {
    val userData by viewModel.user.observeAsState()
    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    val interactionSource = remember { MutableInteractionSource() }

    val isDialogDeleteVisibility by viewModel.isDeleteDialogVisible.collectAsStateWithLifecycle()
    val mainSheetState = rememberModalBottomSheetState()
    var isContactBottomOpen by rememberSaveable() { mutableStateOf(false) }

    val isContactBottomContent = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var isContactBottomContentOpen by rememberSaveable() { mutableStateOf(false) }
    var chooseBottomSheetState by rememberSaveable() { mutableStateOf(false) }




    SetEdgeToEdge(lightColor = Brand900, darkColor = Brand900)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brand900)
    ) {
        ProfileTopBlock(userData = userData, navigateToPoints = navigateToPoints)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, shape = shape)
                .clip(shape = shape)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                        .clickableWithoutRipple(interactionSource) {
                            onPromocodeNavigate()
                        },
                ) {
                    OneProfileBlockItem(
                        modifier = Modifier.padding(all = 16.dp),
                        iconId = R.drawable.ic_promocode,
                        text = stringResource(id = R.string.promocodes)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                        OneProfileBlockItem(
                            modifier = Modifier.clickableWithoutRipple(interactionSource) {
                                onUserDataNavigate()
                            },
                            iconId = R.drawable.ic_data_24,
                            text = stringResource(id = R.string.my_data)
                        )
                        OneProfileBlockItem(
                            modifier = Modifier
                                .clickableWithoutRipple(interactionSource) {
                                }
                                .padding(top = 15.dp),
                            iconId = R.drawable.ic_lock,
                            text = stringResource(id = R.string.chaange_password)
                        )
                        OneProfileBlockItem(
                            modifier = Modifier
                                .clickableWithoutRipple(interactionSource) {
                                    onNotificationNavigate()
                                }
                                .padding(top = 15.dp),
                            iconId = R.drawable.ic_notif,
                            text = stringResource(id = R.string.notification)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(2.dp, borderColor, RoundedCornerShape(20.dp)),
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                        OneProfileBlockItem(
                            modifier = Modifier.clickableWithoutRipple(interactionSource) {
                                isContactBottomOpen = true
                            },
                            iconId = R.drawable.ic_write_24,
                            text = stringResource(id = R.string.contact_with_us)
                        )
                        OneProfileBlockItem(
                            modifier = Modifier.padding(top = 15.dp),
                            iconId = R.drawable.ic_star_24,
                            text = stringResource(id = R.string.write_review)
                        )
                        OneProfileBlockItem(
                            modifier = Modifier
                                .clickableWithoutRipple(interactionSource) {
                                    onInformationScreenNavigate()
                                }
                                .padding(top = 15.dp),
                            iconId = R.drawable.ic_info_24,
                            text = stringResource(id = R.string.information)
                        )
                    }
                }
                LogoutBlock(onClick = {
                    viewModel.changeVisibilityDeleteDialog(true)
                })
            }
        }
        if (isContactBottomOpen) {
            ContactBottomSheet(
                sheetState = mainSheetState,
                onDismissChange = {
                    isContactBottomOpen = false
                },
                onPhoneClick = {
                    isContactBottomContentOpen = true
                    chooseBottomSheetState = true
                },
                onServiceHelpClick = {
                    chooseBottomSheetState = false
                    isContactBottomContentOpen = true
                }
            )
        }
        if (isDialogDeleteVisibility) {
            AlertDialogComponent(
                textTitle = stringResource(id = R.string.are_you_sure_to_log_out),
                textConfirm = stringResource(id = R.string.exit),
                textDismiss = stringResource(id = R.string.dismiss_logOut),
                onDismissRequest = { viewModel.changeVisibilityDeleteDialog(false) },
                onClickConfirmButton = {
                    //logOut
                }
            )
        }
        if (isContactBottomContentOpen) {
            ContactBottomSheetContent(
                sheetState = isContactBottomContent,
                onDismissChange = {
                    isContactBottomContentOpen = false
                },
                chooseBottomSheet = chooseBottomSheetState
            )
        }
    }
}

@Composable
fun ProfileTopBlock(
    userData: UserDto?,
    navigateToPoints: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = userData?.name ?: "",
            style = typography.l28sfD700,
            color = Color.White
        )
        Text(
            text = userData?.phoneNumber ?: "",
            style = typography.l15sfT600,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyPointsBlock(navigateToPoints = navigateToPoints)
    }
}

@Composable
fun LogoutBlock(
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableWithoutRipple(interactionSource) {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = "",
            tint = Gray700
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = stringResource(id = R.string.logout),
            style = typography.l15sfT600,
            fontSize = 17.sp,
            color = Gray700
        )
    }
}

@Composable
fun OneProfileBlockItem(
    iconId: Int,
    text: String,
    iconSize: Dp = 16.dp,
    isIconVisible: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = Brand900
        )
        Spacer(modifier = Modifier.width(13.dp))
        Text(
            text = text,
            style = typography.l15sfT600,
            fontSize = 17.sp,
            color = Dark1000
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isIconVisible) {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .rotate(180f),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "",
                tint = Brand900
            )
        }
    }
}

@Composable
fun MyPointsBlock(
    navigateToPoints: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .clickableWithoutRipple(interactionSource) {
                navigateToPoints()
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFCCB995), Color(0xFFC0AD88))
                ),
                RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_points),
                    style = typography.l15sfT600,
                    color = Color.White
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(12.dp),
                    painter = painterResource(id = R.drawable.ic_right_12),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(13.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.ic_bonus),
                    contentDescription = "",
                    tint = Color.White
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    text = "500",
                    style = typography.l28sfD700,
                    color = Color.White
                )
            }
        }
        Image(
            modifier = Modifier.padding(start = 130.dp),
            painter = painterResource(id = R.drawable.icon_tree),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.2f))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBottomSheet(
    sheetState: SheetState,
    onDismissChange: (Boolean) -> Unit,
    onPhoneClick: () -> Unit,
    onServiceHelpClick: () -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
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
                .padding(all = 24.dp),
        ) {
            Text(
                text = stringResource(id = R.string.contact_with_us),
                style = typography.l20sfD600
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                    .clickableWithoutRipple(interactionSource) {
                        onPhoneClick()
                    },
            ) {
                OneProfileBlockItem(
                    modifier = Modifier
                        .padding(all = 20.dp),
                    iconId = R.drawable.ic_phone_24,
                    text = stringResource(id = R.string.call),
                    iconSize = 32.dp,
                    isIconVisible = false
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                    .clickableWithoutRipple(interactionSource) {
                        onServiceHelpClick()
                    },
            ) {
                OneProfileBlockItem(
                    modifier = Modifier.padding(all = 20.dp),
                    iconId = R.drawable.ic_message_32,
                    text = stringResource(id = R.string.support_service),
                    iconSize = 32.dp,
                    isIconVisible = false
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(2.dp, borderColor, RoundedCornerShape(20.dp)),
            ) {
                OneProfileBlockItem(
                    modifier = Modifier.padding(all = 20.dp),
                    iconId = R.drawable.ic_whatsapp_32,
                    text = stringResource(id = R.string.whatsapp),
                    iconSize = 32.dp,
                    isIconVisible = false
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactBottomSheetContent(
    sheetState: SheetState,
    onDismissChange: (Boolean) -> Unit,
    chooseBottomSheet: Boolean,
) {
    val dialLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {}

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissChange(false) },
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (chooseBottomSheet) {
                PhoneNumberBottomSheetContent(
                    onCancelClick = {
                        onDismissChange(false)
                    },
                    onNumberClick = {
                        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${"87783337459"}")
                        }
                        dialLauncher.launch(dialIntent)
                    },
                )
            } else {
                ServiceHelpBottomSheetContent()
            }
        }
    }
}


@Composable
fun PhoneNumberBottomSheetContent(
    onCancelClick: () -> Unit,
    onNumberClick: () -> Unit,
) {
    Column(
        modifier = Modifier.height(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.clickable {
                onNumberClick()
            },
            text = "87783337459",
            style = typography.l20sfD600,
            fontSize = 24.sp,
            color = Dark1000
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.clickable {
                onCancelClick()
            },
            text = stringResource(id = R.string.cancel),
            style = typography.l20sfD600,
            fontSize = 20.sp,
            color = Blue1000
        )
    }
}

@Composable
fun ServiceHelpBottomSheetContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = ""
            )
            Text(
                text = stringResource(id = R.string.support_service),
                style = typography.l15sf700,
                fontSize = 17.sp,
                color = Color.Black
            )
            Box {}
        }
        SampleTextField()
        Spacer(modifier = Modifier.weight(1f))
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(id = R.string.send),
            onButtonClick = { },
            enabled = true
        )
    }
}