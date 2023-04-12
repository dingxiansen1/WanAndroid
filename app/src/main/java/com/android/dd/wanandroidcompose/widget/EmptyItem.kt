package com.android.dd.wanandroidcompose.widget

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.dd.wanandroidcompose.R


@Composable
fun EmptyItem() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            AsyncImage(
                model = R.mipmap.empty,
                contentDescription = null,
                modifier = Modifier
                    .width(600.dp)
                    .height(400.dp)
            )
        }
    }
}