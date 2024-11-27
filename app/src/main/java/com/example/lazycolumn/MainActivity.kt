package com.example.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazycolumn.ui.theme.LazyColumnTheme
import com.example.lazycolumn.model.Task



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                TaskListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen() {
    // Lista inicial de tareas
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "Comprar comida", false),
                Task(2, "Estudiar Kotlin", false),
                Task(3, "Hacer ejercicio", true)
            )
        )
    }

    // Función para actualizar el estado de una tarea
    fun toggleTaskCompletion(taskId: Int) {
        tasks = tasks.map { task ->
            if (task.id == taskId) task.copy(isCompleted = !task.isCompleted) else task
        }
    }

    // Scaffold con topBar y LazyColumn
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Tareas") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        // LazyColumn para la lista de tareas
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Respeta el espacio de la barra superior
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                TaskItem(task, onToggleCompletion = { toggleTaskCompletion(task.id) })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggleCompletion: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Ícono de estado de la tarea usando imágenes
            Icon(
                painter = painterResource(
                    id = if (task.isCompleted) R.drawable.ic_check else R.drawable.ic_pending
                ),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            // Título de la tarea
            Text(
                text = task.title,
                fontSize = 18.sp,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                color = if (task.isCompleted) Color.Gray else Color.Black,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )

            // Botón para completar la tarea con color rosado
            Button(
                onClick = onToggleCompletion,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF48FB1), // Color rosado
                    contentColor = Color.White
                )
            ) {
                Text(if (task.isCompleted) "Desmarcar" else "Completar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    LazyColumnTheme {
        TaskListScreen()
    }
}