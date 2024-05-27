@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drevmassapp.presentation.profileScreen.myPoints

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.drevmassapp.R
import com.example.drevmassapp.common.DashedLine
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.data.model.BonusDto
import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.data.model.Transaction
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Coral1000
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyPointsScreen(
    viewModel: MyPointsViewModel,
    navigateBack: () -> Unit,
) {

    val bonusState by viewModel.pointsState.collectAsStateWithLifecycle()
    val bonusInfo by viewModel.bonusInfo.observeAsState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var isInfoSheetOpen by rememberSaveable { mutableStateOf(false) }


    when (bonusState) {
        is PointsState.Loading -> {
            ProgressBlock()
            Log.d("MyPointsScreen", "loading")
        }

        is PointsState.Success -> {
            val item = (bonusState as PointsState.Success).bonus
            MyPointsContent(
                navigateBack = navigateBack,
                item = item,
                viewModel = viewModel,
                sheetState = sheetState,
                isInfoSheetOpen = isInfoSheetOpen,
                onSheetStateChange = {
                    isInfoSheetOpen = it
                },
                bonusInfoDto = bonusInfo
            )
            Log.d("MyPointsScreen", "succcess")

        }

        is PointsState.Failure -> {
            Log.d("MyPointsScreen", "failure")

        }

        else -> {}
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyPointsContent(
    navigateBack: () -> Unit,
    item: BonusDto,
    sheetState: SheetState,
    isInfoSheetOpen: Boolean,
    onSheetStateChange: (Boolean) -> Unit,
    bonusInfoDto: BonusInfoDto?,
    viewModel: MyPointsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brand900)
    ) {
        Box(modifier = Modifier) {
            PointsScreenTopBlockContent(
                navigateBack = navigateBack,
                onSheetStateChange = onSheetStateChange,
                viewModel = viewModel
            )
            Image(
                modifier = Modifier
                    .padding(start = 120.dp, bottom = 50.dp),
                painter = painterResource(id = R.drawable.group),
                contentDescription = "",
                colorFilter = ColorFilter.tint(White.copy(alpha = 0.2f))
            )
            if (item.transactions.isEmpty()) {
                EmptyPointsContent()
            } else {
                NotEmptyContent(item.transactions, viewModel = viewModel)
            }
        }
        if (isInfoSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { onSheetStateChange(false) },
                dragHandle = { BottomSheetDefaults.DragHandle() },
                containerColor = White
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onSheetStateChange(true) },
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = Brand900
                        )
                        Text(
                            text = stringResource(id = R.string.bonus_programm),
                            style = typography.l15sfT600,
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                        Box {}
                    }
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text = bonusInfoDto?.text ?: "text",
                        style = typography.l17sfT400,
                        fontSize = 16.sp,
                        color = Dark1000
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyPointsContent() {
    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (180).dp)
            .background(White, shape = shape)
            .clip(shape = shape)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.hiatory_of_points),
                style = typography.l20sfD600
            )
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(112.dp),
                        painter = painterResource(id = R.drawable.ill_epmty_bonuses),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(id = R.string.empty_bonuses_title),
                        style = typography.l15sfT600,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = stringResource(id = R.string.empty_bonuses_subTitle),
                        style = typography.l15sfT600,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotEmptyContent(
    transaction: List<Transaction>,
    viewModel: MyPointsViewModel
) {
    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = (180).dp)
            .background(White, shape = shape)
            .clip(shape = shape)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.hiatory_of_points),
                style = typography.l20sfD600
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = transaction) {
                    BonusListItem(item = it, viewModel = viewModel)
                    DashedLine()
                }
            }
        }
    }
}

@Composable
fun PointsScreenTopBlockContent(
    navigateBack: () -> Unit,
    onSheetStateChange: (Boolean) -> Unit,
    viewModel: MyPointsViewModel
) {
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clickable { navigateBack() }
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Localized description",
                tint = White
            )
            Text(
                text = stringResource(id = R.string.my_points),
                style = typography.l15sfT600,
                fontSize = 17.sp,
                color = White
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        onSheetStateChange(true)
                        viewModel.getBonusInfo()
                    }
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_info_24),
                contentDescription = "Localized description",
                tint = White
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(id = R.drawable.ic_bonus),
                contentDescription = "",
                tint = White
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = "500",
                style = typography.l28sfD700,
                color = White
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = "1 балл = 1 ₽",
            style = typography.l15sfT600,
            color = White
        )
        Spacer(modifier = Modifier.height(25.dp))

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BonusListItem(
    item: Transaction,
    viewModel: MyPointsViewModel
) {
    val date = viewModel.formatDate(item.transactionDate)
    Log.d("BonusListItem", date)
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier,
                    text = item.description,
                    style = typography.l17sfT400,
                    color = Dark900,
                    fontSize = 15.sp
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = date,
                    style = typography.l17sfT400,
                    color = Gray700,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier,
                    text = "${item.transactionType}${item.promoPrice}",
                    style = typography.l15sf700,
                    color = if (item.transactionType == "-") Coral1000 else Dark900
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_bonus),
                    contentDescription = "",
                    tint = Brand900
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        DashedLine(height = 0.5.dp)
    }
}
