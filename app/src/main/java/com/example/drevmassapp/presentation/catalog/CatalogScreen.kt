@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drevmassapp.presentation.catalog

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.SetEdgeToEdge
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.common.shimmerEffect
import com.example.drevmassapp.data.model.Product
import com.example.drevmassapp.presentation.catalog.ListType.*
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand500
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.typography
import com.example.drevmassapp.util.Constant.IMAGE_URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    navigateToProductDetails: (Int) -> Unit
) {
    val radioOptions = listOf(
        stringResource(id = R.string.sort_popular),
        stringResource(id = R.string.sort_priceUp),
        stringResource(id = R.string.sort_priceDown)
    )

    val catalogState = viewModel.catalogState.collectAsStateWithLifecycle()
    val listType by viewModel.listType.observeAsState(initial = GRID)
    val selectedOption by viewModel.selectedOption.observeAsState(radioOptions[0])

    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    val interactionSource = remember { MutableInteractionSource() }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }
    var filterText by rememberSaveable { mutableStateOf(selectedOption) }


    SetEdgeToEdge(lightColor = Brand400, darkColor = Brand400)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brand400)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.navigation_item_catalog),
                style = typography.l28sfD700
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, shape = shape)
                .clip(shape)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            isSheetOpen = true
                        },
                        painter = painterResource(id = R.drawable.ic_sort_up),
                        contentDescription = "",
                        tint = Gray700
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = filterText,
                        style = typography.l15sf700,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.clickableWithoutRipple(interactionSource) {
                            viewModel.toggleListType()
                        },
                        painter = painterResource(
                            id = when (listType) {
                                GRID -> R.drawable.ic_tile
                                VERTICAL_COLUMN -> R.drawable.ic_vertical_type
                                HORIZONTAL_COLUMN -> R.drawable.ic_horizontal_type
                            }
                        ),
                        contentDescription = "",
                        tint = Gray700
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                when (val currentState = catalogState.value) {
                    is CatalogState.Loading -> {
                        when (listType) {
                            GRID -> {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    items(8) {
                                        GridListTypeShimmerItem()
                                    }
                                }
                            }

                            VERTICAL_COLUMN -> {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    items(8) {
                                        VerticalGridShimmerItem()
                                    }
                                }
                            }

                            HORIZONTAL_COLUMN -> {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    items(8) {
                                        HorizontalGridShimmerItem()
                                    }
                                }

                            }
                        }
                    }

                    is CatalogState.Failure -> {
                        Log.d("CatalogScreen", "error ${currentState.message}")
                    }

                    is CatalogState.Products -> {
                        filterText = selectedOption
                        CatalogContent(
                            list = currentState.products,
                            navigateToProductDetails = navigateToProductDetails,
                            listType = listType
                        )
                    }

                    is CatalogState.FamousProducts -> {
                        filterText = stringResource(id = R.string.sort_popular)

                        CatalogContent(
                            list = currentState.famousProducts,
                            navigateToProductDetails = navigateToProductDetails,
                            listType = listType
                        )

                    }

                    is CatalogState.ProductsPriceUp -> {
                        filterText = stringResource(id = R.string.sort_priceUp)

                        CatalogContent(
                            list = currentState.productsPriceUp,
                            navigateToProductDetails = navigateToProductDetails,
                            listType = listType
                        )

                    }

                    is CatalogState.ProductsPriceDown -> {
                        filterText = stringResource(id = R.string.sort_priceDown)

                        CatalogContent(
                            list = currentState.productsPriceDown,
                            navigateToProductDetails = navigateToProductDetails,
                            listType = listType
                        )
                    }

                    else -> {}
                }
            }
        }
    }
    if (isSheetOpen) {
        SortBottomSheet(
            sheetState = sheetState,
            onBottomStateChange = { isSheetOpen = false },
            viewModel = viewModel,
            radioOptionsList = radioOptions,
            selectedOption = selectedOption
        )
    }
}

@Composable
fun CatalogContent(
    list: List<Product>,
    listType: ListType,
    navigateToProductDetails: (Int) -> Unit
) {
    when (listType) {
        GRID -> GridListType(
            list = list,
            navigateToProductDetails = navigateToProductDetails
        )

        VERTICAL_COLUMN -> VerticalListType(
            list = list,
            navigateToProductDetails = navigateToProductDetails
        )

        HORIZONTAL_COLUMN -> HorizontalListType(
            list = list,
            navigateToProductDetails = navigateToProductDetails
        )
    }
}

@Composable
fun GridListType(
    list: List<Product>,
    navigateToProductDetails: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(items = list) { item ->
            CatalogItem(
                height = 100.dp,
                width = 167.dp,
                fontSize = 15.sp,
                item = item,
                imageExtractor = { "$IMAGE_URL${item.imageSrc}" },
                priceExtractor = { "${it.price} ₽" },
                titleExtractor = { it.title },
                onItemClickListener = {
                    navigateToProductDetails(item.id)
                },
                isItemInBasket = {
                    it.basketCount > 0
                }
            )
        }
    }
}

@Composable
fun VerticalListType(
    list: List<Product>,
    navigateToProductDetails: (Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(items = list) { item ->
            CatalogItem(
                modifier = Modifier.fillMaxWidth(),
                height = 202.dp,
                fontSize = 20.sp,
                item = item,
                imageExtractor = { "$IMAGE_URL${item.imageSrc}" },
                priceExtractor = { "${it.price} ₽" },
                titleExtractor = { it.title },
                onItemClickListener = {
                    navigateToProductDetails(item.id)
                },
                isItemInBasket = {
                    it.basketCount > 0
                }
            )
        }
    }
}

@Composable
fun HorizontalListType(
    list: List<Product>,
    navigateToProductDetails: (Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(items = list) { item ->
            CatalogHorizontalItem(
                item = item,
                onItemClickListener = {
                    navigateToProductDetails(item.id)
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = 16.dp),
                thickness = 1.dp,
                color = Color(0xFFECEBEB)
            )
        }
    }
}

@Composable
fun CatalogHorizontalItem(
    item: Product,
    onItemClickListener: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickableWithoutRipple(interactionSource) { onItemClickListener() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(145.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .width(145.dp)
                    .height(90.dp),
                model = "$IMAGE_URL${item.imageSrc}",
                contentScale = ContentScale.Crop,
                contentDescription = "user history item",
                loading = {}
            )
            Log.d("CatalogHorizontalItem", "$IMAGE_URL${item.imageSrc}")
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = item.title,
                style = typography.l17sfT400,
                fontSize = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.price.toString() + "₽",
                    style = typography.l15sf700
                )
                Spacer(modifier = Modifier.weight(1f))
                RoundedBoxIcon()
            }
        }
    }
}

@Composable
fun SortBottomSheet(
    sheetState: SheetState,
    onBottomStateChange: (Boolean) -> Unit,
    selectedOption: String,
    viewModel: CatalogViewModel,
    radioOptionsList: List<String>
) {


    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onBottomStateChange(false) },
        containerColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sort),
                style = typography.l20sfD600
            )
            Spacer(modifier = Modifier.height(16.dp))

            radioOptionsList.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                viewModel.updateSelectedOption(text)
                                when (text) {
                                    radioOptionsList[0] -> viewModel.getFamousProducts()
                                    radioOptionsList[1] -> viewModel.getProductsPriceUp()
                                    radioOptionsList[2] -> viewModel.getProductsPriceDown()
                                }
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = text)
                    Spacer(modifier = Modifier.weight(1f))
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = {
                            viewModel.updateSelectedOption(text)
                            when (text) {
                                radioOptionsList[0] -> viewModel.getFamousProducts()
                                radioOptionsList[1] -> viewModel.getProductsPriceUp()
                                radioOptionsList[2] -> viewModel.getProductsPriceDown()
                            }
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Brand900,
                            unselectedColor = Brand500
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun GridListTypeShimmerItem() {
    Column(
        modifier = Modifier.width(165.dp)
    ) {
        Spacer(
            modifier = Modifier
                .width(165.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
        )
        Row(
            modifier = Modifier
                .padding(top = 12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Spacer(
                    modifier = Modifier
                        .width(64.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(112.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(64.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
            Spacer(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
    }
}


@Composable
fun VerticalGridShimmerItem() {
    Column(modifier = Modifier.width(343.dp)) {
        Spacer(
            modifier = Modifier
                .width(343.dp)
                .height(202.dp)
                .clip(RoundedCornerShape(24.dp))
                .shimmerEffect()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Spacer(
                    modifier = Modifier
                        .width(90.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(
                    modifier = Modifier
                        .padding(top = 9.dp)
                        .width(220.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
            Spacer(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
    }
}

@Composable
fun HorizontalGridShimmerItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
    ) {
        Spacer(
            modifier = Modifier
                .width(146.dp)
                .height(88.dp)
                .clip(RoundedCornerShape(16.dp))
                .shimmerEffect()
        )

        Column(modifier = Modifier.padding(start = 12.dp)) {
            Spacer(
                modifier = Modifier
                    .width(112.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .width(112.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(
                    modifier = Modifier
                        .width(64.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
@Preview
fun wekfwekp() {
    HorizontalGridShimmerItem()
}
