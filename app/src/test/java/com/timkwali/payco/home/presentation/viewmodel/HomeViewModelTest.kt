package com.timkwali.payco.home.presentation.viewmodel

import app.cash.turbine.test
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.home.domain.usecase.GetCards
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getCards: GetCards = mockk()
    private lateinit var homeViewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        homeViewModel = HomeViewModel(getCards)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on add click emits NavigateToAddCard`() = runTest {
        homeViewModel.uiEffect.test {
            homeViewModel.onEvent(HomeEvent.OnAddClick)
            assert(awaitItem() is HomeUiEffect.NavigateToAddCard)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on card click emits NavigateToCardDetails`() = runTest {
        val card = Card(id = 1, cardNumber = "1234", expiryDate = "12/24", cvv = "123", amount = 500)

        homeViewModel.uiEffect.test {
            homeViewModel.onEvent(HomeEvent.OnCardClick(card))
            val effect = awaitItem()
            assert(effect is HomeUiEffect.NavigateToCardDetails && effect.card == card)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on refresh emits error effect when fetch fails`() = runTest {
        coEvery { getCards() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error("Failed"))
        }

        homeViewModel.uiEffect.test {
            homeViewModel.onEvent(HomeEvent.OnRefresh)
            val effect = awaitItem()
            assert(effect is HomeUiEffect.ShowSnackbar && effect.message == "Failed")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `update user info updates name and email`() {
        val name = "John Doe"
        val email = "john@test.com"

        homeViewModel.updateUserInfo(name, email)

        val state = homeViewModel.homeState.value
        assert(state.name == name)
        assert(state.email == email)
    }
}