package com.timkwali.payco.carddetails.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.payco.carddetails.domain.model.CardDetailsState
import com.timkwali.payco.carddetails.domain.usecase.DeleteCard
import com.timkwali.payco.carddetails.domain.usecase.GetCard
import com.timkwali.payco.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val deleteCard: DeleteCard,
    private val getCard: GetCard
): ViewModel() {
    private var _cardDetailsState: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState())
    val cardDetailsState: StateFlow<CardDetailsState> get() = _cardDetailsState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<CardDetailsUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: CardDetailsEvent) {
        when(event) {
            is CardDetailsEvent.DeleteCard -> delete()
            is CardDetailsEvent.Refresh -> fetchCard()
        }
    }

    private fun delete() = viewModelScope.launch(Dispatchers.IO) {
        deleteCard.invoke(_cardDetailsState.value.cardId).collectLatest {
            _cardDetailsState.value = _cardDetailsState.value.copy(isLoading = it is Resource.Loading)
            when(it) {
                is Resource.Success<*> -> {
                    it.data?.let { deleted ->
                        _cardDetailsState.value = _cardDetailsState.value.copy(deleteCardResponse = deleted)
                        _uiEffect.emit(CardDetailsUiEffect.BackToDashboard)
                    }
                }
                is Resource.Error<*> -> { _uiEffect.emit(CardDetailsUiEffect.ShowSnackbar(it.message ?: "Error deleting Card")) }
                else -> Unit
            }
        }
    }

    private fun fetchCard() = viewModelScope.launch(Dispatchers.IO) {
        getCard(_cardDetailsState.value.cardId).collectLatest {
            _cardDetailsState.value = _cardDetailsState.value.copy(isLoading = it is Resource.Loading)
            when(it) {
                is Resource.Success<*> -> { it.data?.let { card -> _cardDetailsState.value = _cardDetailsState.value.copy(card = card) } }
                is Resource.Error<*> -> { _uiEffect.emit(CardDetailsUiEffect.ShowSnackbar(it.message ?: "Error getting Card")) }
                else -> Unit
            }
        }
    }

    fun updateCardDetailsState(cardId: Int, name: String) {
        _cardDetailsState.value = _cardDetailsState.value.copy(cardId = cardId, name = name)
    }
}