@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.drevmassapp.presentation.catalog

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.ProgressBlock
import com.example.drevmassapp.data.model.Product
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.RadioButtonColor
import com.example.drevmassapp.ui.theme.typography

@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel
) {
    val catalogState = viewModel.catalogState.collectAsStateWithLifecycle()
    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brand400)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
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
                        filterText = stringResource(id = R.string.sort_popular)
                    )
                }

                is CatalogState.FamousProducts -> {
                    CatalogContent(
                        viewModel = viewModel,
                        list = currentState.famousProducts,
                        filterText = stringResource(id = R.string.sort_popular)
                    )
                }

                is CatalogState.ProductsPriceUp -> {
                    CatalogContent(
                        viewModel = viewModel,
                        list = currentState.productsPriceUp,
                        filterText = stringResource(id = R.string.sort_priceUp)
                    )
                }

                is CatalogState.ProductsPriceDown -> {
                    CatalogContent(
                        viewModel = viewModel,
                        list = currentState.productsPriceDown,
                        filterText = stringResource(id = R.string.sort_priceDown)
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
    filterText: String
) {

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(all = 16.dp)) {
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
                painter = painterResource(id = R.drawable.ic_tile),
                contentDescription = "",
                tint = Gray700
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(items = list) { item ->
                CatalogItem(
                    height = 100.dp,
                    width = 167.dp,
                    fontSize = 15.sp,
                    item = item
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

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
fun CatalogItem(
    height: Dp = 0.dp,
    width: Dp = 0.dp,
    fontSize: TextUnit = 0.sp,
    item: Product
) {
    Column(
        modifier = Modifier
            .width(width)
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .height(height)
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.price.toString() + "₽",
                    style = typography.l15sf700,
                    fontSize = fontSize
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.title,
                    style = typography.l15sf700,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            RoundedBoxIcon()
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
                            unselectedColor = RadioButtonColor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun RoundedBoxIcon() {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(Brand900, CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = R.drawable.ic_basket),
            contentDescription = "icon basket",
            tint = Color.White
        )
    }
}

