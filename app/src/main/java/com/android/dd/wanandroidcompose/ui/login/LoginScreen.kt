package com.android.dd.wanandroidcompose.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.dd.wanandroidcompose.R
import com.dd.basiccompose.controller.LocalNavController
import com.dd.basiccompose.ext.clickableNoRipple

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    nav: NavHostController = LocalNavController.current,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.toastMsg.collect {
            snackbarHostState.showSnackbar(
                it
            )
        }
    }
    LoginScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        navigateUp = nav::navigateUp,
        updateAccount = viewModel::updateAccount,
        updatePassword = viewModel::updatePassword,
        updateLoginState = viewModel::updateLoginState,
        login = viewModel::login,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: LoginUiState,
    snackbarHostState: SnackbarHostState,
    navigateUp: () -> Unit,
    updateAccount: (String) -> Unit,
    updatePassword: (String) -> Unit,
    updateLoginState: () -> Unit,
    login: () -> Unit,
) {
    LaunchedEffect(key1 = uiState.login) {
        if (uiState.login) {
            navigateUp.invoke()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .statusBarsPadding()
                    .height(250.dp)
            ) {
                IconButton(
                    modifier = Modifier.size(56.dp),
                    onClick = {
                        navigateUp.invoke()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentDescription = Icons.Outlined.ArrowBack.name
                    )
                }

                AsyncImage(
                    model = R.mipmap.logo,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )

                Text(
                    text = stringResource(id = R.string.welcome_to_use),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                Text(
                    text = stringResource(id = R.string.welcome_tips),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.7f),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 5.dp),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Input(
                text = uiState.account,
                label = stringResource(id = R.string.please_enter_a_account),
                leadingIcon = R.mipmap.ic_account_normal,
                keyboardType = KeyboardType.Email
            ) { account ->
                updateAccount.invoke(account)
            }

            Input(
                text = uiState.password,
                label = stringResource(id = R.string.please_enter_a_password),
                leadingIcon = R.mipmap.ic_password_normal,
                keyboardType = KeyboardType.Password,
                isPassword = true
            ) { password ->
                updatePassword.invoke(password)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp, end = 60.dp)
                    .wrapContentHeight()
            ) {
                Checkbox(
                    checked = uiState.isRegisterState,
                    onCheckedChange = {
                        updateLoginState.invoke()
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.primaryContainer,
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                    )
                )
                Text(
                    text = stringResource(id = R.string.login_tips),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
                    .height(40.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(20.dp)
                    )
                    .clip(shape = RoundedCornerShape(20.dp))
                    .clickable {
                        login.invoke()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (uiState.isRegisterState)
                        stringResource(id = R.string.register)
                    else stringResource(id = R.string.login),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.wrapContentSize(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Input(
    text: String,
    label: String = "",
    leadingIcon: Int = 0,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    onValueChanged: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(!isPassword) }
    var hasFocus by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp, end = 60.dp)
            .wrapContentHeight()
    ) {
        TextField(
            value = text,
            onValueChange = onValueChanged,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    hasFocus = it.hasFocus
                },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            leadingIcon = {
                AsyncImage(
                    model = leadingIcon,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = hasFocus && text.trim().isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row {
                        if (isPassword) {
                            Image(
                                painter = painterResource(id = R.mipmap.ic_eye_normal),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(if (hasFocus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer),
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(25.dp)
                                    .clickableNoRipple {
                                        passwordVisible = !passwordVisible
                                    },
                            )
                        }
                        Image(
                            painter = painterResource(id = R.mipmap.ic_delete),
                            contentDescription = null,
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .size(25.dp)
                                .clickableNoRipple {
                                    onValueChanged("")
                                }
                        )
                    }
                }
            },
            shape = RectangleShape,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )

        Divider(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        )
    }
}