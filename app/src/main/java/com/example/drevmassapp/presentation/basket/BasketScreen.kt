package com.example.drevmassapp.presentation.basket

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.AlertDialogComponent
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.DashedLine
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.Basket
import com.example.drevmassapp.data.model.BasketResponseDto
import com.example.drevmassapp.data.model.Product
import com.example.drevmassapp.presentation.catalog.detail.RecommendBlock
import com.example.drevmassapp.ui.theme.Brand300
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Coral1000
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray400
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.GrayDefault
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography
import com.example.drevmassapp.util.Constant


@Composable
fun BasketScreen(
    viewModel: BasketViewModel
) {

    val basketState = viewModel.basketState.collectAsStateWithLifecycle()
    val isDialogDeleteVisibility by viewModel.isDeleteDialogVisible.collectAsStateWithLifecycle()

    val scrollState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0
        }
    }
    val currentState = basketState.value

    LaunchedEffect(Unit) {
        viewModel.getBasket("")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            item {
                HeaderScrollingContent(
                    onDeleteIconClick = { viewModel.changeVisibilityDeleteDialog(true) }
                )
            }
            item {
                BasketContent(
                    viewModel = viewModel,
                    currentState = currentState,
                    isDialogDeleteVisibility = isDialogDeleteVisibility,
                    modifier = Modifier
                        .offset(y = (-12).dp),
                )
            }
        }
        TransformingTopBar(
            isVisible = isScrolled,
            onDeleteIconClick = { viewModel.changeVisibilityDeleteDialog(true) }
        )
    }
}

@Composable
fun HeaderScrollingContent(
    onDeleteIconClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(Brand400)
            .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 35.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.basket),
                color = Color.Black,
                style = typography.l28sfD700,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .clickable { onDeleteIconClick() }
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Delete Icon",
                tint = Gray700
            )
        }
    }
}


@Composable
fun BasketContent(
    currentState: BasketState,
    isDialogDeleteVisibility: Boolean,
    viewModel: BasketViewModel,
    modifier: Modifier = Modifier
) {
    var isChecked by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(16.dp)
    ) {
        when (currentState) {
            is BasketState.Success -> {
                val basketList = currentState.basket.basket

                if (basketList.isNotEmpty()) {
                    BasketContentSuccess(
                        basketState = currentState,
                        isChecked = isChecked,
                        isDialogDeleteVisibility = isDialogDeleteVisibility,
                        viewModel = viewModel,
                        onCheckedChange = { isChecked = it }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyStateBasket()
                    }
                }
            }

            is BasketState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .size(40.dp),
                        color = Brand900
                    )
                }
            }

            is BasketState.Initial -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyStateBasket()
                }
            }

            is BasketState.Failure -> {
                // Handle failure state if needed
            }
        }
    }
}

@Composable
fun BasketContentSuccess(
    basketState: BasketState.Success,
    isChecked: Boolean,
    isDialogDeleteVisibility: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    viewModel: BasketViewModel
) {
    val basketList = basketState.basket.basket

    Column {
        basketList.forEachIndexed { index, item ->
            var basketCount by rememberSaveable {
                mutableStateOf(item.count)
            }

            if (index > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    thickness = 1.5.dp,
                    color = Gray400
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            BasketItem(
                item = item,
                onDecreaseClick = {
                    viewModel.decreaseItem(basketCount, item.productId, 0)
                    basketCount--
                },
                onIncreaseClick = {
                    viewModel.increaseItem(basketCount, item.productId, 0)
                    basketCount++
                },
                count = basketCount
            )
        }

        if (basketList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(32.dp))
            BonusBlock(
                isChecked = isChecked,
                onCheckedChange = onCheckedChange,
                item = basketState.basket
            )
            Spacer(modifier = Modifier.height(16.dp))

            PromocodeBlock(onPromocodeClick = {})

            Spacer(modifier = Modifier.height(32.dp))

            PriceBlock(item = basketState.basket)

            Spacer(modifier = Modifier.height(32.dp))

            RecommendBlock(
                items = basketState.basket.products,
                imageExtractor = { "${Constant.IMAGE_URL}${it.imageSrc}" },
                priceExtractor = { "${it.price} ₽" },
                titleExtractor = { it.title },
                isItemInBasket = { it.basketCount > 0 }
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (isDialogDeleteVisibility) {
                AlertDialogComponent(
                    textTitle = stringResource(id = R.string.delete_products),
                    textConfirm = stringResource(id = R.string.confirm_delete),
                    textDismiss = stringResource(id = R.string.dismiss_delete),
                    onDismissRequest = { viewModel.changeVisibilityDeleteDialog(false) },
                    onClickConfirmButton = {
                        viewModel.deleteAllBasketItems()
                    }
                )
            }
        }
    }
}


@Composable
fun BonusBlock(
    item: BasketResponseDto,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.take_bonus) + " " + item.bonus,
                style = typography.l15sfT600,
                fontSize = 17.sp
            )
            Icon(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_bonus),
                contentDescription = "",
                tint = Brand900
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Brand900,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = GrayDefault,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.take_bonus_subTitle),
            style = typography.l17sfT400,
            fontSize = 15.sp,
            color = Gray800
        )
    }
}

@Composable
fun PromocodeBlock(
    onPromocodeClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(2.dp, borderColor, RoundedCornerShape(20.dp))
            .clickableWithoutRipple(interactionSource) { onPromocodeClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = R.drawable.ic_promocode),
                contentDescription = "",
                tint = Brand900
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(id = R.string.write_promocode),
                style = typography.l15sfT600,
                fontSize = 17.sp,
                color = Dark1000
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .rotate(180f),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "",
                tint = Brand900
            )
        }
    }
}

@Composable
fun PriceBlock(
    item: BasketResponseDto
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(borderColor, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            RowWithTextBlock(
                textName = "${item.countProducts} товар",
                priceText = item.basketPrice.toString() + " ₽"
            )
            RowWithTextBlock(
                modifier = Modifier.padding(top = 15.dp),
                textName = stringResource(id = R.string.pay_with_bonus),
                priceText = if (item.usedBonus == 0) "0 ₽" else "- ${item.bonus} ₽",
                priceTextColor = Coral1000
            )
            Spacer(modifier = Modifier.height(12.dp))
            DashedLine()
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.itogo),
                    style = typography.l15sfT600,
                    color = Dark1000
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.totalPrice.toString() + " ₽",
                    style = typography.l15sf700,
                    color = Dark1000,
                )
            }
        }
    }
}

@Composable
fun RowWithTextBlock(
    textName: String,
    priceText: String,
    textNameColor: Color = Gray800,
    priceTextColor: Color = Dark1000,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = textName,
            style = typography.l17sfT400,
            fontSize = 13.sp,
            color = textNameColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = priceText,
            style = typography.l17sfT400,
            fontSize = 13.sp,
            color = priceTextColor
        )
    }
}

@Composable
fun BasketItem(
    item: Basket,
    count: Int,
    onDecreaseClick: () -> Unit,
    onIncreaseClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
    ) {
        Box(
            modifier = Modifier
                .width(112.dp)
                .height(76.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "${Constant.IMAGE_URL}${item.productImg}",
                contentScale = ContentScale.Crop,
                contentDescription = "item image",
                loading = {}
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = item.productTitle,
                style = typography.l17sfT400,
                fontSize = 15.sp,
                color = Dark900
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.price.toString() + " ₽",
                    style = typography.l15sf700,
                    color = Dark900
                )
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(32.dp),
                    onClick = {},
                    colors = ButtonColors(
                        containerColor = Brand300,
                        contentColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                        disabledContainerColor = Brand300
                    ),
                    contentPadding = PaddingValues(5.dp),
                    enabled = item.count == 0
                ) {
                    Row(
                        modifier = Modifier.padding(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { onDecreaseClick() }) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(id = R.drawable.ic_minus),
                                contentDescription = "",
                                tint = Brand900
                            )
                        }
                        Text(
                            text = count.toString(),
                            style = typography.l15sfT600,
                            color = Dark900
                        )
                        IconButton(onClick = { onIncreaseClick() }) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(id = R.drawable.ic_plus),
                                contentDescription = "",
                                tint = Brand900
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateBasket() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(112.dp),
            painter = painterResource(id = R.drawable.image_empty_basket),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.empty_basket_title),
            style = typography.l15sfT600,
            fontSize = 17.sp,
            color = Dark1000
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.empty_basket_subTitle),
            style = typography.l17sfT400,
            fontSize = 16.sp,
            color = Gray700,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        CustomButton(
            modifier = Modifier
                .width(200.dp)
                .height(56.dp),
            text = stringResource(id = R.string.to_catalog),
            onButtonClick = { } //navigateToCatalog
        )
    }
}


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun TransformingTopBar(
    isVisible: Boolean,
    onDeleteIconClick: () -> Unit,
) {
    val alpha by animateFloatAsState(if (isVisible) 1f else 0f)
    val offsetY by animateFloatAsState(if (isVisible) 0f else 50f)

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .offset(y = offsetY.dp)
            .alpha(alpha),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Box {}
            Text(
                text = stringResource(id = R.string.basket),
                color = Color.Black,
                style = typography.l15sfT600,
                fontSize = 17.sp
            )
            Icon(
                modifier = Modifier
                    .clickable { onDeleteIconClick() }
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Delete Icon",
                tint = Gray700
            )
        }
    }
}




