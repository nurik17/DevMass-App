package com.example.drevmassapp.presentation.catalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.common.ShimmerBox
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.ui.theme.Brand400
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.typography

@Composable
fun <T> CatalogItem(
    height: Dp = 0.dp,
    width: Dp = 0.dp,
    fontSize: TextUnit = 0.sp,
    item: T,
    imageExtractor: () -> String,
    priceExtractor: (T) -> String,
    titleExtractor: (T) -> String,
    isItemInBasket: (T) -> Boolean,
    onItemClickListener: () -> Unit = {},
    modifier : Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .width(width)
            .wrapContentHeight()
            .clickableWithoutRipple(interactionSource) { onItemClickListener() }
    ) {
        Box(
            modifier = modifier
                .width(width)
                .height(height)
                .clip(RoundedCornerShape(10.dp))
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageExtractor(),
                contentScale = ContentScale.Crop,
                contentDescription = "item image",
                loading = {
                    ShimmerBox(
                        height = height,
                        width = width
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = priceExtractor(item),
                    style = typography.l15sf700,
                    fontSize = fontSize
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = titleExtractor(item),
                    style = typography.l15sf700,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (isItemInBasket(item)) {
                CheckedRoundedBoxIcon()
            } else {
                RoundedBoxIcon()
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

@Composable
fun CheckedRoundedBoxIcon() {
    Box(
        modifier = Modifier
            .size(36.dp)
            .border(BorderStroke(2.dp, Brand400), shape = CircleShape)
            .background(color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = R.drawable.ic_busket_add_20),
            contentDescription = "icon basket",
            tint = Brand900
        )
    }
}

