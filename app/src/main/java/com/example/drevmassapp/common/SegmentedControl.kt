package com.example.drevmassapp.common

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.drevmassapp.R
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray400
import com.example.drevmassapp.ui.theme.Gray600
import com.example.drevmassapp.ui.theme.typography

@Composable
fun SegmentedControl(
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Dp = 10.dp,
    @ColorRes color: Int = R.color.white,
    onItemSelection: (selectedItemIndex: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = remember { mutableStateOf(defaultSelectedItemIndex) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Gray400, RoundedCornerShape(10.dp))
            .wrapContentHeight()
    ) {
        items.forEachIndexed { index, item ->
            Box(contentAlignment = Alignment.Center) {
                Button(
                    modifier = when (index) {
                        0 -> {
                            if (useFixedWidth) {
                                Modifier
                                    .padding(3.dp)
                                    .width(itemWidth)
                                    .height(32.dp)
                                    .zIndex(if (selectedIndex.value == index) 1f else 0f)
                            } else {
                                Modifier
                                    .padding(3.dp)
                                    .height(32.dp)
                                    .zIndex(if (selectedIndex.value == index) 1f else 0f)
                            }
                        }

                        else -> {
                            if (useFixedWidth)
                                Modifier
                                    .padding(3.dp)
                                    .width(itemWidth)
                                    .height(32.dp)
                                    .zIndex(if (selectedIndex.value == index) 1f else 0f)
                            else Modifier
                                .padding(3.dp)
                                .height(32.dp)
                                .zIndex(if (selectedIndex.value == index) 1f else 0f)
                        }
                    },
                    onClick = {
                        selectedIndex.value = index
                        onItemSelection(selectedIndex.value)
                    },
                    shape = when (index) {
                        /**
                         * left outer button
                         */
                        /**
                         * left outer button
                         */
                        0 -> RoundedCornerShape(
                            topStart = cornerRadius,
                            topEnd = cornerRadius,
                            bottomStart = cornerRadius,
                            bottomEnd = cornerRadius
                        )
                        /**
                         * right outer button
                         */
                        /**
                         * right outer button
                         */
                        items.size - 1 -> RoundedCornerShape(
                            topStart = cornerRadius,
                            topEnd = cornerRadius,
                            bottomStart = cornerRadius,
                            bottomEnd = cornerRadius
                        )
                        /**
                         * middle button
                         */
                        /**
                         * middle button
                         */
                        else -> RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    },
                    /*border = BorderStroke(
                        1.dp, if (selectedIndex.value == index) {
                            colorResource(id = color)
                        } else {
                            colorResource(id = R.color.black)
                        }
                    ),*/
                    colors = if (selectedIndex.value == index) {
                        /**
                         * selected colors
                         */
                        /**
                         * selected colors
                         */
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = if (selectedIndex.value == index) {
                                colorResource(id = color)
                            } else {
                                colorResource(id = R.color.gray)
                            }
                        )
                    } else {
                        /**
                         * not selected colors
                         */
                        /**
                         * not selected colors
                         */
                        ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                    },
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item,
                        style = typography.l15sfT600,
                        fontSize = 12.sp,
                        color = Dark1000,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}