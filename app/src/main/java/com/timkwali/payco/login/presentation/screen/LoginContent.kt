package com.timkwali.payco.login.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.timkwali.payco.R
import com.timkwali.payco.core.presentation.components.button.PaycoButton
import com.timkwali.payco.core.presentation.components.image.IconFromDrawable
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.text.TitleText
import com.timkwali.payco.core.presentation.components.textfield.PaycoTextField
import com.timkwali.payco.login.domain.model.LoginState
import com.timkwali.payco.login.presentation.viewmodel.LoginEvent
import com.timkwali.payco.login.presentation.viewmodel.LoginUiEffect
import com.timkwali.payco.login.presentation.viewmodel.LoginViewModel
import kotlin.math.log


@Composable
fun LoginContent(
    loginState: LoginState,
    effect: LoginUiEffect?,
    onNavigate: () -> Unit,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            is LoginUiEffect.NavigateToDashboard -> onNavigate()
            is LoginUiEffect.ShowSnackbar -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier  = modifier
            .padding(16.dp),
    ) {

        TitleText(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(bottom = 80.dp),
            fontSize = 35.sp
        )

        PaycoTextField(
            value = loginState.email,
            enabled = !loginState.isLoading,
            onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
            leadingIcon = { IconFromDrawable(R.drawable.ic_email, modifier = Modifier.size(24.dp)) },
            label = { BodyText(text = stringResource(R.string.email_hint), color = colorScheme.tertiary.copy(alpha = 0.5f)) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        PaycoTextField(
            value = loginState.password,
            enabled = !loginState.isLoading,
            onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
            leadingIcon = { IconFromDrawable(R.drawable.ic_key, modifier = Modifier.size(24.dp)) },
            label = { BodyText(text = stringResource(R.string.password_hint), color = colorScheme.tertiary.copy(alpha = 0.5f)) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(50.dp))

        PaycoButton(
            text = stringResource(R.string.login),
            enabled = !loginState.isLoading,
            onClick = { onEvent(LoginEvent.Submit) }
        )

        if(loginState.isLoading){
            CircularProgressIndicator(
                color = colorScheme.secondary,
                modifier = Modifier.padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun LoginContentPreview() {
    LoginContent(LoginState(), null, {}, {})
}
