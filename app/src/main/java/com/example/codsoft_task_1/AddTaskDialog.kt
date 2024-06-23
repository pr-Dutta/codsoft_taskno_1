package com.example.codsoft_task_1

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.codsoft_task_1.Data.TaskEvent
import com.example.codsoft_task_1.Data.TaskState
import java.util.Calendar
import java.util.Date

@Composable
fun AddTaskDialog(
    context: Context,
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        {_: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/${month + 1}/$year"
        }, year, month, day,
    )


    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(TaskEvent.HideAddDialog)
        },
        containerColor = Color(0xffcaf0f8),
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(TaskEvent.SetDueDate(date.value))
                    onEvent(TaskEvent.SaveTask)
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff0077b6),
                        contentColor = Color.White,
                    )) {
                    Text(text = "Save")
                }
            }
        },
        dismissButton = {

        },
        title = {
            Text(
                text = "Add Task",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff0077b6)
            )
        },
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

                Row(
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Due Date",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            datePickerDialog.show()
                        }
                            .background(
                                Color(0xff0077b6),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = " - ",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = date.value,
                        fontWeight = FontWeight.Bold
                    )
                }

                val selectedPriorityOne = remember { mutableStateOf(false) }
                val selectedPriorityTwo = remember { mutableStateOf(false) }
                val selectedPriorityThree = remember { mutableStateOf(false) }

                Text(
                    text = "Priority",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    //fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        datePickerDialog.show()
                    }
                        .background(
                            Color(0xff0077b6),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedPriorityOne.value,
                        onClick = {
                            selectedPriorityOne.value = !selectedPriorityOne.value

                            if (selectedPriorityOne.value) {
                                onEvent(TaskEvent.SetTaskPriority(1))
                            }
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xff0077b6),
                            unselectedColor = Color(0xff0077b6)
                        )
                    )
                    Text(
                        text = "High",
                        color = Color(0xff0077b6)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = selectedPriorityTwo.value,
                        onClick = {
                            selectedPriorityTwo.value = !selectedPriorityTwo.value

                            if (selectedPriorityTwo.value) {
                                onEvent(TaskEvent.SetTaskPriority(2))
                            }
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xff0077b6),
                            unselectedColor = Color(0xff0077b6)
                        )
                    )
                    Text(
                        text = "Medium",
                        color = Color(0xff0077b6)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = selectedPriorityThree.value,
                        onClick = {
                            selectedPriorityThree.value = !selectedPriorityThree.value

                            if (selectedPriorityThree.value) {
                                onEvent(TaskEvent.SetTaskPriority(3))
                            }
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xff0077b6),
                            unselectedColor = Color(0xff0077b6)
                        )
                    )
                    Text(
                        text = "Low",
                        color = Color(0xff0077b6)
                    )
                }
            }
        }
    )
}