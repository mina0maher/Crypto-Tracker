package com.mina.cryptotracker.crypto.domain

import com.mina.cryptotracker.core.domain.util.NetworkError
import com.mina.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins():Result<List<Coin>, NetworkError>
    
}