package com.timkwali.payco.login.presentation.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.timkwali.payco.core.presentation.navigation.Routes
import com.timkwali.payco.login.presentation.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavHostController) {
    val loginViewModel: LoginViewModel = koinViewModel()
    val loginState = loginViewModel.loginState.collectAsStateWithLifecycle()
    val uiEffect = loginViewModel.uiEffect.collectAsStateWithLifecycle(null)

    LoginContent(
        loginState = loginState.value,
        effect = uiEffect.value,
        onNavigate = {
            navController.navigate("${Routes.Home}/${loginState.value.loginResponse?.name}/${loginState.value.loginResponse?.email}")
        },
        onEvent = { loginViewModel.onEvent(it) },
    )
}