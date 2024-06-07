package com.example.drevmassapp.presentation.profileScreen.information

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.drevmassapp.R
import com.example.drevmassapp.common.SetEdgeToEdge
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.ui.theme.Blue1000
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray400
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    navigateBack: () -> Unit,
    viewModel: InformationScreenViewModel = hiltViewModel()
) {
    SetEdgeToEdge(lightColor = Color.White, darkColor = Color.White)

    val supportInfo by viewModel.supportInfo.observeAsState()

    var isBottomSheetOpen by rememberSaveable() { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var chooseBottomSheet by rememberSaveable() { mutableStateOf(false) }

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
                        text = stringResource(id = R.string.information),
                        style = typography.l15sfT600,
                        fontSize = 17.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                },
                actions = {},
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
                .padding(all = 16.dp)
        ) {
            TextWithEndIcon(
                text = stringResource(id = R.string.about_company),
                onClick = {
                    isBottomSheetOpen = true
                    chooseBottomSheet = true
                },
            )
            TextWithEndIcon(
                text = stringResource(id = R.string.about_app),
                onClick = {
                    chooseBottomSheet = false
                    isBottomSheetOpen = true
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            SocialNetworkBlock()
            Spacer(modifier = Modifier.height(36.dp))

            if (isBottomSheetOpen) {
                BottomSheetContent(
                    sheetState = sheetState,
                    onDismissChange = {
                        isBottomSheetOpen = false
                    },
                    chooseBottomSheet = chooseBottomSheet,
                    item = supportInfo
                )
            }
        }
    }
}

@Composable
fun SocialNetworkBlock() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.we_in_social_network),
            style = typography.l17sfT400,
            fontSize = 15.sp,
            color = Gray800
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconWithRoundedBox(
                iconId = R.drawable.ic_youtube,
                url = "https://www.youtube.com/@Drevmass"
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconWithRoundedBox(
                iconId = R.drawable.ic_vk,
                url = "https://vk.com/drevmass"
            )
        }
    }
}

@Composable
fun IconWithRoundedBox(
    iconId: Int,
    url: String
) {
    val context = LocalContext.current

    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(Brand400, CircleShape)
            .clip(CircleShape)
            .clickableWithoutRipple(interactionSource) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier,
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = Brand900
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    sheetState: SheetState,
    onDismissChange: (Boolean) -> Unit,
    chooseBottomSheet: Boolean,
    item: BonusInfoDto?,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismissChange(false) },
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (chooseBottomSheet) {
                AboutCompanyBottomSheetContent(
                    item = item,
                    onCloseClick = {
                        onDismissChange(false)
                    }
                )
            } else {
                AboutAppBottomSheetContent(
                    onCloseClick = {
                        onDismissChange(false)
                    }
                )
            }
        }
    }
}

@Composable
fun AboutCompanyBottomSheetContent(
    item: BonusInfoDto?,
    onCloseClick:() ->Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        BottomSheetTopBlock(text = stringResource(id = R.string.about_company), onCloseClick = onCloseClick)
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Transparent, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp)),
            painter = painterResource(id = R.drawable.support_info_image),
            contentDescription = "",
        )
        Text(
            text = item?.text ?: "",
            style = typography.l17sfT400,
            fontSize = 16.sp,
            color = Dark900
        )
    }
}

@Composable
fun AboutAppBottomSheetContent(
    onCloseClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomSheetTopBlock(text = stringResource(id = R.string.about_company), onCloseClick = onCloseClick)
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .size(112.dp)
                .background(Brand400, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp),
                painter = painterResource(id = R.drawable.ic_logo_app),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.app_name_russian),
            style = typography.l28sfD700,
            fontSize = 22.sp,
            color = Dark900
        )
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = "Версия 1.0.4",
            style = typography.l17sfT400,
            fontSize = 15.sp,
            color = Gray800
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.app_info), style = typography.l17sfT400,
            fontSize = 15.sp,
            color = Gray800,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TextWithEndIcon(
    text: String,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(modifier = Modifier.padding(top = 15.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickableWithoutRipple(interactionSource) {
                    onClick()
                }
        ) {
            Text(
                text = text,
                style = typography.l15sfT600,
                fontSize = 17.sp,
                color = Dark1000
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_right_12),
                contentDescription = "",
                tint = Gray700
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 15.dp),
            color = Gray400
        )
    }
}

@Composable
fun BottomSheetTopBlock(
    text: String,
    onCloseClick:() ->Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(1f)) {}
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = typography.l15sfT600,
            fontSize = 17.sp,
            color = Dark1000,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 50.dp)
                .clickable {
                    onCloseClick()
                },
            text = stringResource(id = R.string.close),
            style = typography.l15sfT600,
            color = Blue1000
        )
    }
}
