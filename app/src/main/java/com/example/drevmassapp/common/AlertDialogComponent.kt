package com.example.drevmassapp.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drevmassapp.ui.theme.Blue1000
import com.example.drevmassapp.ui.theme.Coral1000
import com.example.drevmassapp.ui.theme.Gray800
import com.example.drevmassapp.ui.theme.typography


@Composable
fun AlertDialogComponent(
    textTitle: String = "",
    textConfirm: String = "",
    textDismiss: String = "",
    onDismissRequest: () -> Unit = {},
    onClickConfirmButton: () -> Unit = {},
    subTitleText: String = "",
    modifier: Modifier = Modifier,
) {

    AlertDialog(
        modifier = modifier
            .background(
                Color.Transparent, RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp)),
        onDismissRequest = { onDismissRequest() },
        shape = RoundedCornerShape(15.dp),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = textTitle,
                style = typography.l15sfT600,
                color = Color.Black,
                fontSize = 17.sp,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subTitleText,
                style = typography.l17sfT400,
                fontSize = 13.sp,
                color = Gray800,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest.invoke()
                    onClickConfirmButton()
                }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = textConfirm,
                    style = typography.l17sfT400,
                    fontSize = 20.sp,
                    color = Coral1000,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest.invoke()
                }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = textDismiss,
                    style = typography.l17sfT400,
                    color = Blue1000,
                    textAlign = TextAlign.Center
                )
            }
        },
        containerColor = Color.White,
    )
}