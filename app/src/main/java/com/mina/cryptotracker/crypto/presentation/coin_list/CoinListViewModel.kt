package com.mina.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mina.cryptotracker.core.domain.util.onError
import com.mina.cryptotracker.core.domain.util.onSuccess
import com.mina.cryptotracker.crypto.domain.CoinDataSource
import com.mina.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource:CoinDataSource
):ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart {
            loadCoins()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action:CoinListAction){
        when(action){
            //is CoinListAction.OnCoinClick -> {}
//            CoinListAction.OnRefresh ->{
//                loadCoins()
//            }
            is CoinListAction.OnCoinClick -> {
                _state.update {
                    CoinListState(
                        isLoading = it.isLoading,
                        coins = it.coins,
                        selectedCoin = action.coinUi
                    )
                }
            }
        }
    }
    private fun loadCoins(){
        viewModelScope.launch {
            _state.update {
                CoinListState(
                    isLoading = true,
                    coins = it.coins,
                    selectedCoin = it.selectedCoin
                )
            }
            coinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update {
                        CoinListState(
                            isLoading = false,
                            coins = coins.map{
                                it.toCoinUi()
                            },
                            selectedCoin = it.selectedCoin
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        CoinListState(
                            isLoading = false,
                            coins = it.coins,
                            selectedCoin = it.selectedCoin
                        )
                    }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}