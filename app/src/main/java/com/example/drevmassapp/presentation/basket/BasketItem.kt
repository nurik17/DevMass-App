package com.example.drevmassapp.presentation.basket

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.drevmassapp.R
import com.example.drevmassapp.data.model.Basket
import com.example.drevmassapp.ui.theme.Brand300
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.typography
import com.example.drevmassapp.util.Constant

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
                        IconButton(
                            onClick = { onDecreaseClick() },
                            enabled = count > 0
                        ) {
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