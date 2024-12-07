package com.mina.cryptotracker.crypto.data.networking

import com.mina.cryptotracker.core.domain.util.NetworkError
import com.mina.cryptotracker.core.domain.util.Result
import com.mina.cryptotracker.core.domain.util.map
import com.mina.cryptotracker.crypto.data.mappers.toCoin
import com.mina.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.mina.cryptotracker.crypto.domain.Coin
import com.mina.cryptotracker.crypto.domain.CoinDataSource
import com.mina.cryptotracker.data.networking.constructUrl
import com.mina.cryptotracker.data.networking.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource (
    private val httpClient:HttpClient
):CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall <CoinsResponseDto>{
            httpClient.get(
                urlString = constructUrl("assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }

        }
    }

}