package com.example.drevmassapp.presentation.profileScreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drevmassapp.R
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.borderColor
import com.example.drevmassapp.ui.theme.typography

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navigateToPoints: () -> Unit
) {
    val userData by viewModel.user.observeAsState()
    val shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    val interactionSource = remember { MutableInteractionSource() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brand900)
    ) {
        ProfileTopBlock(userData = userData, navigateToPoints = navigateToPoints)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, shape = shape)
                .clip(shape = shape)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                        .clickableWithoutRipple(interactionSource) { },
                ) {
                    OneProfileBlockItem(
                        modifier = Modifier.padding(all = 16.dp),
                        iconId = R.drawable.ic_promocode,
                        text = stringResource(id = R.string.write_promocode)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                        .clickableWithoutRipple(interactionSource) { }
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                        OneProfileBlockItem(
                            iconId = R.drawable.ic_promocode,
                            text = stringResource(id = R.string.write_promocode)
                        )
                        OneProfileBlockItem(
                            modifier = Modifier.padding(top = 15.dp),
                            iconId = R.drawable.ic_lock,
                            text = stringResource(id = R.string.chaange_password)
                        )
                        OneProfileBlockItem(
                            modifier = Modifier.padding(top = 15.dp),
                            iconId = R.drawable.ic_notif,
                            text = stringResource(id = R.string.notification)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                        .clickableWithoutRipple(interactionSource) { },
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                        OneProfileBlockItem(
                            iconId = R.drawable.ic_promocode,
                            text = stringResource(id = R.string.contact_with_us)
                        )
                        OneProfileBlockItem(
                            modifier = Modifier.padding(top = 15.dp),
                            iconId = R.drawable.ic_lock,
                            text = stringResource(id = R.string.write_review)
                        )
                        OneProfileBlockItem(
                            modifier = Modifier.padding(top = 15.dp),
                            iconId = R.drawable.ic_notif,
                            text = stringResource(id = R.string.information)
                        )
                    }
                }
                LogoutBlock()
            }
        }
    }
}

@Composable
fun ProfileTopBlock(
    userData: UserDto?,
    navigateToPoints: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = userData?.name ?: "",
            style = typography.l28sfD700,
            color = Color.White
        )
        Text(
            text = userData?.phoneNumber ?: "",
            style = typography.l15sfT600,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        MyPointsBlock(navigateToPoints = navigateToPoints)
    }
}

@Composable
fun LogoutBlock() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_logout),
            contentDescription = "",
            tint = Gray700
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = stringResource(id = R.string.logout),
            style = typography.l15sfT600,
            fontSize = 17.sp,
            color = Gray700
        )
    }
}

@Composable
fun OneProfileBlockItem(
    iconId: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = Brand900
        )
        Spacer(modifier = Modifier.width(13.dp))
        Text(
            text = text,
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

@Composable
fun MyPointsBlock(
    navigateToPoints: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .clickableWithoutRipple(interactionSource) {
                navigateToPoints()
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFCCB995), Color(0xFFC0AD88))
                ),
                RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_points),
                    style = typography.l15sfT600,
                    color = Color.White
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(12.dp),
                    painter = painterResource(id = R.drawable.ic_right_12),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(13.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.ic_bonus),
                    contentDescription = "",
                    tint = Color.White
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    text = "500",
                    style = typography.l28sfD700,
                    color = Color.White
                )
            }
        }
        Image(
            modifier = Modifier.padding(start = 130.dp),
            painter = painterResource(id = R.drawable.icon_tree),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.2f))
        )
    }
}