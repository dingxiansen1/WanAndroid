package com.android.dd.wanandroidcompose.ui.navigator.tutorial

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.entity.Classify
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go

@Composable
fun TutorialScreen(
    viewModel: TutorialViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState by viewModel.lazyListState.collectAsStateWithLifecycle()
    LazyColumn(
        state = lazyListState,
    ) {
        itemsIndexed(uiState, { index, item -> item.id }) { index, item ->
            TutorialItem(item) {
                nav.go(RouteName.tutorialDescArguments(item.id))
            }
        }
    }
}

@Composable
fun TutorialItem(item: Classify, function: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                function.invoke()
            },
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(90.dp)
                    .height(120.dp),
                placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                error = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                model = item.cover,
                contentDescription = "cover"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = item.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.author, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.desc,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
