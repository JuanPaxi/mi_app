package com.panfilosoft.moduleapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panfilosoft.moduleapp.state.UserIntent
import com.panfilosoft.moduleapp.state.ViewState
import com.panfilosoft.moduleapp.viewmodel.MyViewModel

@Composable
fun MyScreen(viewModel: MyViewModel = viewModel()) {

    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botones de acciones
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { viewModel.processIntent(UserIntent.LoadData) }) {
                Text("Cargar Datos")
            }
            Button(onClick = { viewModel.processIntent(UserIntent.Refresh) }) {
                Text("Refrescar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de búsqueda
        var query by remember { mutableStateOf("") }
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.processIntent(UserIntent.Search(it))
            },
            label = { Text("Buscar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Renderizado de estado
        when (state) {
            is ViewState.Idle    -> Text("Presiona un botón para comenzar.")
            is ViewState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            is ViewState.Data    -> {
                val items = (state as ViewState.Data).items
                LazyColumn {
                    items(items) { item ->
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
            }

            is ViewState.Error   -> Text(
                text = (state as ViewState.Error).message,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}