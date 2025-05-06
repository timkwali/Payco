package com.timkwali.payco.payment.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.payment.domain.model.PaymentState
import com.timkwali.payco.payment.domain.usecase.GetAmountOwed
import com.timkwali.payco.payment.domain.usecase.GetCards
import com.timkwali.payco.payment.domain.usecase.PayAmount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val getCards: GetCards,
    private val getAmountOwed: GetAmountOwed,
    private val payAmount: PayAmount
): ViewModel() {
    private var _paymentState: MutableStateFlow<PaymentState> = MutableStateFlow(PaymentState())
    val paymentState: StateFlow<PaymentState> get() = _paymentState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<PaymentUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: PaymentEvent) {
        when(event) {
            is PaymentEvent.OnCurrentCardValueChange -> { _paymentState.value = _paymentState.value.copy(currentCard = event.card) }
            is PaymentEvent.OnCardSelect -> { _paymentState.value = _paymentState.value.copy(currentCard = event.card) }
            is PaymentEvent.OnPay -> executePayment()
            is PaymentEvent.OnRefresh -> {
                fetchCards()
                fetchAmountToPay()
            }
        }
    }

    private fun fetchCards() = viewModelScope.launch(Dispatchers.IO) {
        getCards.invoke().collectLatest {
            _paymentState.value = _paymentState.value.copy(isLoading = it is Resource.Loading)
            when(it) {
                is Resource.Success<*> -> { it.data?.let { cards -> _paymentState.value = _paymentState.value.copy(cards = cards) } }
                is Resource.Error<*> -> _uiEffect.emit(PaymentUiEffect.ShowSnackbar(it.message ?: "Error fetching cards."))
                else -> Unit
            }
        }
    }

    private fun fetchAmountToPay() = viewModelScope.launch(Dispatchers.IO) {
        getAmountOwed.invoke().collectLatest {
            _paymentState.value = _paymentState.value.copy(isLoading = it is Resource.Loading)
            when(it) {
                is Resource.Success<*> -> { it.data?.let { amount -> _paymentState.value = _paymentState.value.copy(amountToPay = amount) } }
                is Resource.Error<*> -> _uiEffect.emit(PaymentUiEffect.ShowSnackbar(it.message ?: "Error fetching amount to pay."))
                else -> Unit
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun executePayment()  = viewModelScope.launch(Dispatchers.IO) {
        payAmount.invoke(paymentState.value.currentCard).collectLatest {
            _paymentState.value = _paymentState.value.copy(isLoading = it is Resource.Loading)
            when(it) {
                is Resource.Success<*> -> {
                    _uiEffect.emit(PaymentUiEffect.ShowSnackbar("Payment Successful"))
                    _uiEffect.emit(PaymentUiEffect.NavigateToDashboard)
                }
                is Resource.Error<*> -> _uiEffect.emit(PaymentUiEffect.ShowSnackbar(it.message ?: "Error completing payment."))
                else -> Unit
            }
        }
    }
}