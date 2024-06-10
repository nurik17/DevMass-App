package com.example.drevmassapp.presentation.catalog.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.DashedLine
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.ProductDetailItemDto
import com.example.drevmassapp.data.model.Recommend
import com.example.drevmassapp.presentation.catalog.CatalogItem
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography
import com.example.drevmassapp.util.Constant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(
    id: String?,
    navigateBack: () -> Unit,
    navigateToBasket: () -> Unit,
    viewModel: ProductDetailViewModel,
) {
    val detailState = viewModel.detailState.collectAsStateWithLifecycle()
    val currentState = detailState.value
    val scrollState = rememberScrollState()

    val density = LocalDensity.current
    val showTitleThreshold = with(density) { 270.dp.toPx() }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.getProductById(id.toInt())
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (currentState is ProductDetailState.Success) {
                        Text(
                            text = if (scrollState.value > showTitleThreshold)
                                currentState.productDetail.product.title
                            else "",
                            style = typography.l15sfT600,
                            fontSize = 17.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = Brand900
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "",
                            tint = Brand900
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .padding(top = 10.dp),
        content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (currentState) {
                        is ProductDetailState.Loading -> {
                            ProgressBlock()
                        }

                        is ProductDetailState.Failure -> {

                        }

                        is ProductDetailState.Success -> {
                            id?.let {
                                DetailScreenContent(
                                    recommendedList = currentState.productDetail.recommend,
                                    item = currentState.productDetail.product,
                                    navigateToBasket = navigateToBasket,
                                    scrollState = scrollState,
                                    id = it.toInt(),
                                    viewModel = viewModel
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
    )
}


@Composable
fun DetailScreenContent(
    recommendedList: List<Recommend>,
    item: ProductDetailItemDto,
    navigateToBasket: () -> Unit,
    scrollState: ScrollState,
    id: Int,
    viewModel: ProductDetailViewModel
) {
    val buttonUiType by viewModel.basketButtonUiType.observeAsState(initial = AddBasketButtonType.DEFAULT)
    val interactionSource = remember { MutableInteractionSource() }

    var basketCount by rememberSaveable {
        mutableStateOf(item.basketCount)
    }

    LaunchedEffect(Unit) {
        viewModel.checkButtonState(item)
    }

    var howToUseButtonY by remember { mutableStateOf(0f) }
    val screenHeight =
        with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = "${Constant.IMAGE_URL}${item.imageSrc}",
                    contentScale = ContentScale.Crop,
                    contentDescription = "user history item",
                    loading = {}
                )
            }

            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = item.title,
                    style = typography.l17sfT400,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = item.price.toString() + " ₽",
                    style = typography.l28sfD700,
                )

                Spacer(modifier = Modifier.height(16.dp))
                when (buttonUiType) {
                    AddBasketButtonType.DEFAULT -> {
                        CustomButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            text = stringResource(id = R.string.to_basket),
                            onButtonClick = {
                                viewModel.addToBasket(count = 1, productId = id, userId = 0)
                                basketCount++
                                viewModel.changeButtonUiType(id, AddBasketButtonType.ADD_BASKET)
                            }
                        )
                    }

                    AddBasketButtonType.ADD_BASKET -> {
                        AddToBasketTypeBlock(
                            interactionSource = interactionSource,
                            navigateToBasket = navigateToBasket,
                            count = basketCount,
                            onIncreaseClick = {
                                viewModel.increaseItem(basketCount, id, 0)
                                basketCount++
                            },
                            onDecreaseClick = {
                                if (basketCount == 0) {
                                    viewModel.changeButtonUiType(id, AddBasketButtonType.DEFAULT)
                                } else {
                                    viewModel.decreaseItem(basketCount, id, 0)
                                    basketCount--
                                }
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                        .onGloballyPositioned { coordinates ->
                            howToUseButtonY = coordinates.positionInParent().y
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_youtube),
                        contentDescription = "",
                        tint = Brand900
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = stringResource(id = R.string.how_to_use),
                        style = typography.l15sfT600,
                        color = Brand900
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                ProductSizeInfoBlock(item = item)

                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(id = R.string.description),
                    style = typography.l20sfD600
                )

                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = item.description,
                    style = typography.l17sfT400,
                    color = Gray800,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                RecommendBlock(
                    items = recommendedList,
                    titleText = stringResource(id = R.string.buy_with_this),
                    imageExtractor = { "${Constant.IMAGE_URL}${it.image_src}" },
                    priceExtractor = { "${it.price} ₽" },
                    titleExtractor = { it.title },
                    isItemInBasket = { it.basketCount > 0 }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        if (howToUseButtonY - scrollState.value <= screenHeight) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                when (buttonUiType) {
                    AddBasketButtonType.DEFAULT -> {
                        CustomButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            text = stringResource(id = R.string.to_basket),
                            onButtonClick = {
                                viewModel.addToBasket(count = 1, productId = id, userId = 0)
                                basketCount++
                                viewModel.changeButtonUiType(id, AddBasketButtonType.ADD_BASKET)
                            }
                        )
                    }

                    AddBasketButtonType.ADD_BASKET -> {
                        AddToBasketTypeBlock(
                            interactionSource = interactionSource,
                            navigateToBasket = navigateToBasket,
                            count = basketCount,
                            onIncreaseClick = {
                                viewModel.increaseItem(basketCount, id, 0)
                                basketCount++
                            },
                            onDecreaseClick = {
                                if (basketCount == 0) {
                                    viewModel.changeButtonUiType(id, AddBasketButtonType.DEFAULT)
                                } else {
                                    viewModel.decreaseItem(basketCount, id, 0)
                                    basketCount--
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


/*
@Composable
fun DetailScreenContent(
    recommendedList: List<Recommend>,
    item: ProductDetailItemDto,
    navigateToBasket: () -> Unit,
    scrollState: ScrollState,
    id: Int,
    viewModel: ProductDetailViewModel
) {
    val buttonUiType by viewModel.basketButtonUiType.observeAsState(initial = AddBasketButtonType.DEFAULT)
    val interactionSource = remember { MutableInteractionSource() }

    var basketCount by rememberSaveable {
        mutableStateOf(item.basketCount)
    }

    LaunchedEffect(Unit) {
        viewModel.checkButtonState(item)
    }

    var howToUseButtonY by remember { mutableStateOf(0f) }
    val screenHeight = with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = "${Constant.IMAGE_URL}${item.imageSrc}",
                    contentScale = ContentScale.Crop,
                    contentDescription = "user history item",
                    loading = {}
                )
            }

            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
            ) {
                Text(
                    text = item.title,
                    style = typography.l17sfT400,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = item.price.toString() + " ₽",
                    style = typography.l28sfD700,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                        .onGloballyPositioned { coordinates ->
                            howToUseButtonY = coordinates.positionInParent().y
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_youtube),
                        contentDescription = "",
                        tint = Brand900
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = stringResource(id = R.string.how_to_use),
                        style = typography.l15sfT600,
                        color = Brand900
                    )
                }

                when (buttonUiType) {
                    AddBasketButtonType.DEFAULT -> {
                        CustomButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            text = stringResource(id = R.string.to_basket),
                            onButtonClick = {
                                viewModel.addToBasket(count = 1, productId = id, userId = 0)
                                basketCount++
                                viewModel.changeButtonUiType(id, AddBasketButtonType.ADD_BASKET)
                            }
                        )
                    }

                    AddBasketButtonType.ADD_BASKET -> {
                        AddToBasketTypeBlock(
                            interactionSource = interactionSource,
                            navigateToBasket = navigateToBasket,
                            count = basketCount,
                            onIncreaseClick = {
                                viewModel.increaseItem(basketCount, id, 0)
                                basketCount++
                            },
                            onDecreaseClick = {
                                if (basketCount == 0) {
                                    viewModel.changeButtonUiType(id, AddBasketButtonType.DEFAULT)
                                } else {
                                    viewModel.decreaseItem(basketCount, id, 0)
                                    basketCount--
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                ProductSizeInfoBlock(item = item)

                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(id = R.string.description),
                    style = typography.l20sfD600
                )

                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = item.description,
                    style = typography.l17sfT400,
                    color = Gray800,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                RecommendBlock(recommendedList = recommendedList)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        val buttonVisibility by viewModel.buttonVisibility.collectAsStateWithLifecycle()
        // Bottom button visibility logic
        LaunchedEffect(scrollState.value) {
            val howToUseButtonPosition = howToUseButtonY - scrollState.value
            val isMiddleButtonVisible = howToUseButtonPosition > 0 && howToUseButtonPosition <= screenHeight
            val isBottomButtonVisible = !isMiddleButtonVisible

            viewModel.changeButtonVisibility(isBottomButtonVisible)
        }

        // Bottom button
        if (buttonVisibility && buttonUiType == AddBasketButtonType.ADD_BASKET) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                AddToBasketTypeBlock(
                    interactionSource = interactionSource,
                    navigateToBasket = navigateToBasket,
                    count = basketCount,
                    onIncreaseClick = {
                        viewModel.increaseItem(basketCount, id, 0)
                        basketCount++
                    },
                    onDecreaseClick = {
                        if (basketCount == 0) {
                            viewModel.changeButtonUiType(id, AddBasketButtonType.DEFAULT)
                        } else {
                            viewModel.decreaseItem(basketCount, id, 0)
                            basketCount--
                        }
                    }
                )
            }
        }
    }
}
*/




@Composable
fun ProductInfoBlock(
    viewModel: ProductDetailViewModel,
    item: ProductDetailItemDto,
    id: Int,
    navigateToBasket: () -> Unit
) {
    val buttonUiType by viewModel.basketButtonUiType.observeAsState(initial = AddBasketButtonType.DEFAULT)
    val interactionSource = remember { MutableInteractionSource() }

    var basketCount by rememberSaveable() {
        mutableStateOf(item.basketCount)
    }

    LaunchedEffect(Unit) {
        viewModel.checkButtonState(item)
    }

    Text(
        text = item.title,
        style = typography.l17sfT400,
    )
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = item.price.toString() + " ₽",
        style = typography.l28sfD700,
    )

    Spacer(modifier = Modifier.height(16.dp))

    when (buttonUiType) {

        AddBasketButtonType.DEFAULT -> {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                text = stringResource(id = R.string.to_basket),
                onButtonClick = {
                    viewModel.addToBasket(count = 1, productId = id, userId = 0)
                    basketCount++
                    viewModel.changeButtonUiType(id, AddBasketButtonType.ADD_BASKET)
                }
            )
        }

        AddBasketButtonType.ADD_BASKET -> {
            AddToBasketTypeBlock(
                interactionSource = interactionSource,
                navigateToBasket = navigateToBasket,
                count = basketCount,
                onIncreaseClick = {
                    viewModel.increaseItem(basketCount, id, 0)
                    basketCount++
                },
                onDecreaseClick = {
                    if (basketCount == 0) {
                        viewModel.changeButtonUiType(id, AddBasketButtonType.DEFAULT)
                    } else {
                        viewModel.decreaseItem(basketCount, id, 0)
                        basketCount--
                    }
                }
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = R.drawable.ic_youtube),
            contentDescription = "",
            tint = Brand900
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = stringResource(id = R.string.how_to_use),
            style = typography.l15sfT600,
            color = Brand900
        )
    }
}

@Composable
fun AddToBasketTypeBlock(
    interactionSource: MutableInteractionSource,
    navigateToBasket: () -> Unit,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    count: Int,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(275.dp)
                .height(56.dp)
                .border(2.dp, Brand900, RoundedCornerShape(50.dp))
                .clip(RoundedCornerShape(50.dp))
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.in_basket),
                    style = typography.l15sfT600,
                    fontSize = 17.sp,
                    color = Brand900
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .clickableWithoutRipple(
                            interactionSource,
                            onClick = { onDecreaseClick() }
                        )
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "",
                    tint = Brand900
                )
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = count.toString(),
                    style = typography.l15sfT600,
                    fontSize = 17.sp
                )
                Icon(
                    modifier = Modifier
                        .clickableWithoutRipple(
                            interactionSource,
                            onClick = { onIncreaseClick() }
                        )
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "",
                    tint = Brand900
                )
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Brand900, CircleShape)
                .clip(CircleShape)
                .clickableWithoutRipple(interactionSource, onClick = { navigateToBasket() }),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ProductSizeInfoBlock(item: ProductDetailItemDto) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .border(2.dp, borderColor, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CardSizeItem(
                iconId = R.drawable.ic_weight,
                name = stringResource(id = R.string.weight),
                sizeText = item.height
            )
            DashedLine()
            CardSizeItem(
                iconId = R.drawable.ic_size,
                name = stringResource(id = R.string.size),
                sizeText = item.size
            )
        }
    }
}

@Composable
fun <T> RecommendBlock(
    items: List<T>,
    imageExtractor: (T) -> String,
    priceExtractor: (T) -> String,
    titleExtractor: (T) -> String,
    isItemInBasket: (T) -> Boolean,
    titleText: String,
) {

    Text(
        modifier = Modifier.padding(bottom = 16.dp),
        text = titleText,
        style = typography.l20sfD600
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items(items = items) { item ->
            CatalogItem(
                height = 100.dp,
                width = 167.dp,
                fontSize = 15.sp,
                item = item,
                imageExtractor = { imageExtractor(item) },
                priceExtractor = { priceExtractor(item) },
                titleExtractor = { titleExtractor(item) },
                isItemInBasket = isItemInBasket
            )
        }
    }
}


@Composable
fun CardSizeItem(
    iconId: Int,
    name: String,
    sizeText: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = Brand900
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = name,
            style = typography.l17sfT400,
            fontSize = 15.sp,
            color = Gray800
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = sizeText,
            style = typography.l15sfT600,
            color = Dark1000
        )
    }
}

