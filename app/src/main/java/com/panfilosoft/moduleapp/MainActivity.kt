package com.panfilosoft.moduleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panfilosoft.moduleapp.ui.theme.ModuleAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModuleAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val useCase = GetGreetingUseCase()
                    val viewModel = MainViewModel(useCase)
                    GreetingScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun GreetingScreen(
    viewModel: MainViewModel
) {
    val message by viewModel.message.collectAsState()
    Text(text = message)
    Button(onClick = {}) { Text(text = "Push Mee!") }
    Button(onClick = { /*TODO*/ }) { Text(text="novo")

    }
}

class MainViewModel(
    private val getGreetingUseCase: GetGreetingUseCase
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    init {
        fetchGreeting()
    }

    private fun fetchGreeting() {
        viewModelScope.launch {
            _message.value = getGreetingUseCase.execute()
        }
    }
}

class GetGreetingUseCase {
    fun execute(): String {
        return "Hai, This is a message"
    }
}