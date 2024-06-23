package com.example.codsoft_task_1

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codsoft_task_1.Data.SortType
import com.example.codsoft_task_1.Data.Task
import com.example.codsoft_task_1.Data.TaskEvent
import com.example.codsoft_task_1.Data.TaskState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    context: Context,
    state: TaskState,
    onEvent: (TaskEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(TaskEvent.ShowAddDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = Color(0xff0077b6)
                )
            }
        }
    ) {

        val updateTask = remember {
            mutableStateOf(Task(0, 0, "", true, "", ""))
        }

        if (state.isAddingTask) {
            AddTaskDialog(context, state = state, onEvent = onEvent)
        }else if (state.isUpdatingTask) {
            UpdateTaskDialog(state = state, updateTask.value, onEvent = onEvent)
        }


        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xff90e0ef))
                    .padding(start = 16.dp, top = 16.dp, bottom = 0.dp)
            ) {
                Text(
                    text = "To-Do List",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff0077b6)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Sort By -",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff0077b6)
                    )

                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(TaskEvent.SortTasks(sortType))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(TaskEvent.SortTasks(sortType))
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xff0077b6),
                                    unselectedColor = Color(0xff0077b6)
                                )
                            )
                            Text(
                                text = sortType.name,
                                color = Color(0xff0077b6)
                            )
                        }
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffcaf0f8)),
            ) {

                items(state.tasks) { task ->

                    lateinit var textDecoration: TextDecoration
                    if (task.isActive) {
                        textDecoration = TextDecoration.None
                    }else {
                        textDecoration = TextDecoration.LineThrough
                    }

                    var backgroundColor: Color = Color.White
                    if (task.priority == 1) {
                        backgroundColor = Color(0xfffb8500)
                    }else if(task.priority == 2) {
                        backgroundColor = Color(0xffe9c46a)
                    }else if (task.priority == 3) {
                        backgroundColor = Color(0xff8ecae6)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                            .clickable {
                                updateTask.value = task
                                onEvent(TaskEvent.SetTaskName(task.taskName))
                                onEvent(TaskEvent.SetTaskDescription(task.taskDescription))
                                onEvent(TaskEvent.ShowUpdateDialog)
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {

                            Text(
                                text = task.taskName,
                                fontSize = 20.sp,
                                textDecoration = textDecoration
                            )
                            task.taskDescription?.let { it1 ->
                                Text(
                                    text = it1,
                                    fontSize = 12.sp,
                                    textDecoration = textDecoration
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {

                            Text(
                                text = task.dueDate,
                                fontSize = 20.sp,
                                textDecoration = textDecoration
                            )
                            Text(
                                text = "Completed By",
                                fontSize = 12.sp,
                                textDecoration = textDecoration
                            )

                        }
                        IconButton(onClick = {
                            onEvent(TaskEvent.DeleteTask(task))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Task",
                                tint = Color(0xff0077b6)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}