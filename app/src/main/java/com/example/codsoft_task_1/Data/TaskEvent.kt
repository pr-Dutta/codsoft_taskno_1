package com.example.codsoft_task_1.Data

sealed interface TaskEvent {
    object SaveTask: TaskEvent
    data class SetTaskName(val taskName: String): TaskEvent
    data class SetTaskDescription(val taskDescription: String?): TaskEvent
    data class SetDueDate(val dueDate: String): TaskEvent
    data class SetTaskPriority(val taskPriority: Int): TaskEvent
    object ShowAddDialog: TaskEvent
    object ShowUpdateDialog: TaskEvent
    object HideAddDialog: TaskEvent
    object HideUpdateDialog: TaskEvent
    data class SortTasks(val sortType: SortType): TaskEvent
    data class DeleteTask(val task: Task): TaskEvent
    data class UpdateTask(val task: Task): TaskEvent
    data class UpdateTaskStatus(val isActive: Boolean): TaskEvent
}