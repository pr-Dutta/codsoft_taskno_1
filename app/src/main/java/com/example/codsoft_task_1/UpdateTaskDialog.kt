package com.example.codsoft_task_1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codsoft_task_1.Data.Task
import com.example.codsoft_task_1.Data.TaskEvent
import com.example.codsoft_task_1.Data.TaskState

@Composable
fun UpdateTaskDialog(
    state: TaskState,
    updateTask: Task,
    onEvent: (TaskEvent) -> Unit,
    modifier: Modifier = Modifier
) {


    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(TaskEvent.HideUpdateDialog)
        },
        containerColor = Color(0xffcaf0f8),
        shape = RoundedCornerShape(8.dp),
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(TaskEvent.UpdateTask(
                        Task(updateTask.id, updateTask.priority, updateTask.dueDate, state.isActive,
                            state.taskName, state.taskDescription))
                    )
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff0077b6),
                        contentColor = Color.White,
                    )) {
                    Text(text = "Save")
                }
            }
        },
        title = { Text(
            text = "Edit Task",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff0077b6)
        ) },
        text = {
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextField(
                    value = state.taskName,
                    onValueChange = {
                        onEvent(TaskEvent.SetTaskName(it))
                    },
                    placeholder = { Text(text = "Task Name") }
                )
                state.taskDescription?.let {
                    TextField(
                        value = it,
                        onValueChange = {
                            onEvent(TaskEvent.SetTaskDescription(it))
                        },
                        placeholder = { Text(text = "Task Description") }
                    )
                }

                val isActive = remember { mutableStateOf(updateTask.isActive) }

                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Task Complete",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff0077b6)
                    )
                    RadioButton(
                        selected = !isActive.value,
                        onClick = {
                            isActive.value = !isActive.value
                            onEvent(TaskEvent.UpdateTaskStatus(isActive.value))
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xff0077b6),
                            unselectedColor = Color(0xff0077b6),
                        )
                    )
                }
            }
        }
    )
}