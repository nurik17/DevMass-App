package com.example.drevmassapp.presentation.catalog.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.CustomButton
import com.example.drevmassapp.common.DashedLine
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.data.model.ProductDetailItemDto
import com.example.drevmassapp.data.model.Recommend
import com.example.drevmassapp.presentation.catalog.CatalogItem
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    id: String?,
    viewModel: ProductDetailViewModel
) {
    val detailState = viewModel.detailState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.getProductById(id.toInt())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { }) {
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
    ) { paddingValues ->
        when (val currentState = detailState.value) {
            is ProductDetailState.Loading -> {
                ProgressBlock()
            }

            is ProductDetailState.Failure -> {

            }

            is ProductDetailState.Success -> {
                DetailScreenContent(
                    paddingValues = paddingValues,
                    recommendedList = currentState.productDetail.recommend,
                    item = currentState.productDetail.product,
                    onBasketClickListener = {}
                )
            }

            else -> {}
        }
    }
}

@Composable
fun DetailScreenContent(
    paddingValues: PaddingValues,
    recommendedList: List<Recommend>,
    item: ProductDetailItemDto,
    onBasketClickListener: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues = paddingValues)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = item.imageSrc,
                contentScale = ContentScale.Crop,
                contentDescription = "user history item",
                loading = {}
            )
        }

        Column(
            modifier = Modifier
                .padding(all = 16.dp)
        ) {

            ProductInfoBlock(onBasketClickListener = onBasketClickListener, item = item)
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
}

@Composable
fun ProductInfoBlock(
    onBasketClickListener: () -> Unit,
    item: ProductDetailItemDto
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

    CustomButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        text = stringResource(id = R.string.to_basket),
        onButtonClick = { onBasketClickListener() }
    )

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
fun RecommendBlock(recommendedList: List<Recommend>) {

    Text(
        modifier = Modifier.padding(bottom = 16.dp),
        text = stringResource(id = R.string.buy_with_this),
        style = typography.l20sfD600
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

        items(items = recommendedList) { item ->
            CatalogItem(
                height = 100.dp,
                width = 167.dp,
                fontSize = 15.sp,
                item = item,
                imageExtractor = { it.image_src },
                priceExtractor = { "${it.price} ₽" },
                titleExtractor = { it.title },
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