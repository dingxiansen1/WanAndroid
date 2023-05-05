package com.android.dd.wanandroidcompose.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.constant.RouteName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel() {
    companion object {
        const val TAG = "MainViewModel"
    }

    val uiState = MutableStateFlow(MainUiState())

    val mainNavigationBarItem = arrayListOf(
        NavigationBarItemData(
            RouteName.MainNavItem.Home,
            Icons.Outlined.Home,
            R.string.home
        ),
        NavigationBarItemData(
            RouteName.MainNavItem.Project,
            Icons.Outlined.Dehaze,
            R.string.project
        ),
        NavigationBarItemData(
            RouteName.MainNavItem.Author,
            Icons.Outlined.Group,
            R.string.author
        ),
        NavigationBarItemData(
            RouteName.MainNavItem.Navigator,
            Icons.Outlined.NearMe,
            R.string.navigator
        ),
        NavigationBarItemData(
            RouteName.MainNavItem.Mine,
            Icons.Outlined.Person,
            R.string.mine
        ),
    )

    fun updateSelectIndex(index: Int) {
        uiState.update {
            it.copy(selectIndex = index)
        }
    }

}

data class MainUiState(
    val selectIndex: Int = 0,
)

data class NavigationBarItemData(
    val nav: String,
    val icon: ImageVector,
    val label: Int,
)