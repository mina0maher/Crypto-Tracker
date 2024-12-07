package com.mina.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.mina.cryptotracker.core.presentation.util.ObserveAsEvents
import com.mina.cryptotracker.core.presentation.util.toString
import com.mina.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.mina.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.mina.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.mina.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.mina.cryptotracker.ui.theme.CryptoTrackerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()
                    val context = LocalContext.current
                    ObserveAsEvents(events = viewModel.events) { event ->
                                    when (event) {
                                        is CoinListEvent.Error -> {
                                            Toast.makeText(
                                                context,
                                                event.error.toString(context),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                    when{
                        state.selectedCoin != null ->{
                            CoinDetailScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        else -> {
                            CoinListScreen(
                                state = state,
                                modifier = Modifier.padding(innerPadding),
                                onAction = viewModel::onAction
                            )
                         }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTrackerTheme {
        Greeting("Android")
    }
}