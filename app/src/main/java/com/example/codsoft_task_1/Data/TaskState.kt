package com.example.codsoft_task_1.Data

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val taskName: String = "",
    val taskDescription: String? = "",
    val taskPriority: Int = 0,
    val dueDate: String = " ",
    val isAddingTask: Boolean = false,
    val isUpdatingTask: Boolean = false,
    val isActive: Boolean = true,
    val sortType: SortType = SortType.PRIORITY,
)
