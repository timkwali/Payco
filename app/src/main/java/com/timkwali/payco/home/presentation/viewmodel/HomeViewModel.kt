package com.timkwali.payco.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.home.domain.model.Card
import com.timkwali.payco.home.domain.model.HomeState
import com.timkwali.payco.home.domain.usecase.GetCards
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCards: GetCards
): ViewModel() {

    private var _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> get() = _homeState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnCardClick -> navigateToCardDetails(event.card)
            is HomeEvent.OnAddClick -> navigateToAddCard()
            is HomeEvent.OnRefresh -> fetchUserDetails()
        }
    }

    private fun navigateToCardDetails(card: Card) = viewModelScope.launch {
        _uiEffect.emit(HomeUiEffect.NavigateToCardDetails(card))
    }

    private fun navigateToAddCard() = viewModelScope.launch {
        _uiEffect.emit(HomeUiEffect.NavigateToAddCard)
    }

    private fun fetchUserDetails() = viewModelScope.launch(Dispatchers.IO) {
        getCards.invoke().collectLatest {
            _homeState.value = _homeState.value.copy(isLoading = it is Resource.Loading)
            when(it) {
                is Resource.Success<*> -> it.data?.let { cards -> _homeState.value = _homeState.value.copy(cards = cards) }
                is Resource.Error<*> -> _uiEffect.emit(HomeUiEffect.ShowSnackbar(it.message ?: "Error fetching Cards."))
                else -> Unit
            }
        }
    }

    fun updateUserInfo(name: String, email: String) {
        _homeState.value = _homeState.value.copy(name = name, email = email)
    }
}