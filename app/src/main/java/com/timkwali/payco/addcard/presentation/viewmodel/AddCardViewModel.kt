package com.timkwali.payco.addcard.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.payco.addcard.domain.model.AddCardState
import com.timkwali.payco.addcard.domain.usecase.AddNewCard
import com.timkwali.payco.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddCardViewModel(
    private val addNewCard: AddNewCard
): ViewModel() {
    private var _addCardState: MutableStateFlow<AddCardState> = MutableStateFlow(AddCardState())
    val addCardState: StateFlow<AddCardState> get() = _addCardState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<AddCardUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()


    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: AddCardEvent) {
        when(event) {
            is AddCardEvent.OnCardNumberChange -> _addCardState.value = _addCardState.value.copy(cardNumber = event.cardNumber)
            is AddCardEvent.OnCvvChange -> _addCardState.value = _addCardState.value.copy(cvv = event.cvv)
            is AddCardEvent.OnExpiryDateChange -> _addCardState.value = _addCardState.value.copy(expiryDate = event.expiryDate)
            is AddCardEvent.Submit -> addCard()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addCard() = viewModelScope.launch(Dispatchers.IO){
        _addCardState.value.apply {
            addNewCard.invoke(
                cardNumber, cvv,
                expiryDate.formatExpiry()
            ).collectLatest {
                _addCardState.value = _addCardState.value.copy(isLoading = it is Resource.Loading)
                when(it) {
                    is Resource.Success<*> -> {
                        it.data?.let { userCard -> _addCardState.value = _addCardState.value.copy(addCardResponse = userCard) }
                        _uiEffect.emit(AddCardUiEffect.BackToDashboard)
                    }
                    is Resource.Error<*> -> _uiEffect.emit(AddCardUiEffect.ShowSnackbar(it.message ?: "Error adding Card."))
                    else -> Unit
                }
            }
        }
    }

    private fun String.formatExpiry(): String {
        return when {
            this.length <= 2 -> this
            else -> this.substring(0, 2) + "/" + this.substring(2)
        }
    }
}