package com.mina.cryptotracker.crypto.presentation.coin_list

import com.mina.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error : NetworkError):CoinListEvent
}