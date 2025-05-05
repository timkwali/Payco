package com.timkwali.payco.login.presentation.viewmodel

import app.cash.turbine.test
import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.login.data.api.model.LoginResponse
import com.timkwali.payco.login.domain.usecase.Login
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import org.koin.test.KoinTest


@ExperimentalCoroutinesApi
class LoginViewModelTest : KoinTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var login: Login
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        login = mockk()

        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(module {
                single { login }
                single { LoginViewModel(get()) }
            })
        }

        viewModel = get(LoginViewModel::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `submit emits NavigateToDashboard on success`() = runTest {
        val loginResponse = LoginResponse("dummy name", "dummy@email.com", "1/1/2025")
        val flow = flowOf(Resource.Loading(), Resource.Success(loginResponse))

        coEvery { login.invoke(any(), any()) } returns flow

        viewModel.onEvent(LoginEvent.OnEmailChange("test@example.com"))
        viewModel.onEvent(LoginEvent.OnPasswordChange("password123"))
        viewModel.onEvent(LoginEvent.Submit)

        viewModel.uiEffect.test {
            awaitItem() shouldBe LoginUiEffect.NavigateToDashboard
            cancelAndIgnoreRemainingEvents()
        }

        assert(viewModel.loginState.value.loginResponse == loginResponse)
    }

    @Test
    fun `submit emits ShowSnackbar on error`() = runTest {
        val flow = flowOf(
            Resource.Loading(),
            Resource.Error<LoginResponse>("Invalid credentials")
        )

        coEvery { login.invoke(any(), any()) } returns flow

        viewModel.onEvent(LoginEvent.OnEmailChange("test@example.com"))
        viewModel.onEvent(LoginEvent.OnPasswordChange("wrongpass"))
        viewModel.onEvent(LoginEvent.Submit)

        viewModel.uiEffect.test {
            val effect = awaitItem()
            assert(effect is LoginUiEffect.ShowSnackbar)
            assert((effect as LoginUiEffect.ShowSnackbar).message == "Invalid credentials")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
