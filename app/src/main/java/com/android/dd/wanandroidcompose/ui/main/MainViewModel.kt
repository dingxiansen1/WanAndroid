package com.android.dd.wanandroidcompose.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.dd.wanandroidcompose.BaseViewModel
import com.android.dd.wanandroidcompose.R
import com.android.dd.wanandroidcompose.constant.RouteName
import com.android.dd.wanandroidcompose.data.ToastData
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
            RouteName.MainRoute.Home,
            Icons.Outlined.Home,
            R.string.home
        ),
        NavigationBarItemData(
            RouteName.MainRoute.Project,
            Icons.Outlined.Dehaze,
            R.string.project
        ),
        NavigationBarItemData(
            RouteName.MainRoute.Author,
            Icons.Outlined.Group,
            R.string.author
        ),
        NavigationBarItemData(
            RouteName.MainRoute.Navigator,
            Icons.Outlined.NearMe,
            R.string.navigator
        ),
        NavigationBarItemData(
            RouteName.MainRoute.Mine,
            Icons.Outlined.Person,
            R.string.mine
        ),
    )

    fun updateSelectIndex(index: Int) {
        uiState.update {
            it.copy(selectIndex = index)
        }
    }

    fun showToast(toastData: ToastData) {
        uiState.value = uiState.value.copy(toastMsg = toastData)
    }

    fun closeToast() {
        uiState.value = uiState.value.copy(toastMsg = ToastData(false, ""))
    }

}

data class MainUiState(
    val toastMsg: ToastData = ToastData(),
    val selectIndex: Int = 0,
)

data class NavigationBarItemData(
    val nav: String,
    val icon: ImageVector,
    val label: Int,
)