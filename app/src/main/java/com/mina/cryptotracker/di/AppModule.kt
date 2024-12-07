package com.mina.cryptotracker.di

import com.mina.cryptotracker.data.networking.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.mina.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.mina.cryptotracker.crypto.domain.CoinDataSource
import com.mina.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind

val appModule = module{
    single{HttpClientFactory.create(CIO.create())}
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    viewModelOf(::CoinListViewModel)

}