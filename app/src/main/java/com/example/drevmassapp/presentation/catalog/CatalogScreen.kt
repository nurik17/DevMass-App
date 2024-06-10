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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.common.SetEdgeToEdge
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.Product
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.Brand500
import com.example.drevmassapp.ui.theme.typography
import com.example.drevmassapp.util.Constant.IMAGE_URL

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    navigateToProductDetails: (Int) -> Unit
) {
    val catalogState = viewModel.catalogState.collectAsStateWithLifecycle()
    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

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
            when (val currentState = catalogState.value) {
                is CatalogState.Loading -> {
                    ProgressBlock()
                }

                is CatalogState.Failure -> {
                    Log.d("CatalogScreen", "error ${currentState.message}")
                }

                is CatalogState.Products -> {
                    CatalogContent(
                        viewModel = viewModel,
                        list = currentState.products,
                        filterText = stringResource(id = R.string.sort_popular),
                        navigateToProductDetails = navigateToProductDetails
                    )
                }

                is CatalogState.FamousProducts -> {
                    CatalogContent(
                        viewModel = viewModel,
                        list = currentState.famousProducts,
                        filterText = stringResource(id = R.string.sort_popular),
                        navigateToProductDetails
                    )
                }

                is CatalogState.ProductsPriceUp -> {
                    CatalogContent(
                        viewModel = viewModel,
                        list = currentState.productsPriceUp,
                        filterText = stringResource(id = R.string.sort_priceUp),
                        navigateToProductDetails = navigateToProductDetails
                    )
                }

                is CatalogState.ProductsPriceDown -> {
                    CatalogContent(
                        viewModel = viewModel,
                        list = currentState.productsPriceDown,
                        filterText = stringResource(id = R.string.sort_priceDown),
                        navigateToProductDetails = navigateToProductDetails
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
fun CatalogContent(
    viewModel: CatalogViewModel,
    list: List<Product>,
    filterText: String,
    navigateToProductDetails: (Int) -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }
    val listType by viewModel.listType.observeAsState(initial = ListType.GRID)
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .padding(start = 16.dp,end = 16.dp,top = 16.dp, bottom = 0.dp)
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
                        ListType.GRID -> R.drawable.ic_tile
                        ListType.VERTICAL_COLUMN -> R.drawable.ic_vertical_type
                        ListType.HORIZONTAL_COLUMN -> R.drawable.ic_horizontal_type
                    }
                ),
                contentDescription = "",
                tint = Gray700
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        when (listType) {
            ListType.GRID -> GridListType(
                list = list,
                navigateToProductDetails = navigateToProductDetails
            )

            ListType.VERTICAL_COLUMN -> VerticalListType(
                list = list,
                navigateToProductDetails = navigateToProductDetails
            )

            ListType.HORIZONTAL_COLUMN -> HorizontalListType(
                list = list,
                navigateToProductDetails = navigateToProductDetails
            )
        }

        Spacer(
            modifier = Modifier
                .background(Color.Red)
                .height(35.dp)
        )

        if (isSheetOpen) {
            SortBottomSheet(
                sheetState = sheetState,
                onBottomStateChange = { isSheetOpen = false },
                viewModel = viewModel
            )
        }
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
                height = 202.dp,
                width = 343.dp,
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
    viewModel: CatalogViewModel
) {
    val radioOptions = listOf(
        stringResource(id = R.string.sort_popular),
        stringResource(id = R.string.sort_priceUp),
        stringResource(id = R.string.sort_priceDown)
    )

    val selectedOption by viewModel.selectedOption.observeAsState(radioOptions[0])

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

            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                viewModel.updateSelectedOption(text)
                                when (text) {
                                    radioOptions[0] -> viewModel.getFamousProducts()
                                    radioOptions[1] -> viewModel.getProductsPriceUp()
                                    radioOptions[2] -> viewModel.getProductsPriceDown()
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
                                radioOptions[0] -> viewModel.getFamousProducts()
                                radioOptions[1] -> viewModel.getProductsPriceUp()
                                radioOptions[2] -> viewModel.getProductsPriceDown()
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



