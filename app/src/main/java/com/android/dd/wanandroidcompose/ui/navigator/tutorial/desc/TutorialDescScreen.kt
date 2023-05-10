package com.android.dd.wanandroidcompose.ui.navigator.tutorial.desc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.entity.Article
import com.android.dd.wanandroidcompose.ui.web.navigation.navigateToWeb
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.navigation.go
import com.dd.basiccompose.widget.DefaultTopBarBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialDescScreen(
    viewModel: TutorialDescViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState by viewModel.lazyListState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            DefaultTopBarBack(title = stringResource(id = R.string.tutorialDesc))
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(
                state = lazyListState,
            ) {
                itemsIndexed(uiState, { index, item -> item.id }) { index, item ->
                    TutorialDescItem(item) {
                        nav.navigateToWeb(item.link, item.title)
                    }
                }
            }
        }
    }
}

@Composable
fun TutorialDescItem(item: Article, function: () -> Unit) {
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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = item.author, style = MaterialTheme.typography.titleMedium)
                Text(text = item.niceDate, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                item.superChapterName?.let {
                    Text(
                        text = item.superChapterName + " · ",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(text = item.chapterName, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}