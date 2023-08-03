package com.android.dd.wanandroidcompose.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewModelScope
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.manager.SnackbarManager
import com.android.dd.wanandroidcompose.manager.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val snackbarManager: SnackbarManager,
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

    fun showToast(msg: String) {
        snackbarManager.showMessage(
            uiText = UiText.DynamicString(msg)
        )
    }
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