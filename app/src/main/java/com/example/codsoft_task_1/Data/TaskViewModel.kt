package com.example.codsoft_task_1.Data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(
    private val dao: TaskDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.PRIORITY)
    private val _tasks = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.PRIORITY -> dao.getTasksOrderedByPriority()
                SortType.TASK_NAME -> dao.getTasksOrderedByTaskName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(TaskState())
    val state = combine(_state, _sortType, _tasks) { state, sortType, tasks ->
        state.copy(
            tasks = tasks,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    dao.deleteTask(event.task)
                }
            }
            TaskEvent.HideAddDialog -> {
                _state.update { it.copy(
                    isAddingTask = false
                ) }
            }
            TaskEvent.HideUpdateDialog -> {
                _state.update { it.copy(
                    isUpdatingTask = false,
                    taskName = "",
                    taskDescription = ""
                ) }
            }
            TaskEvent.SaveTask -> {
                val taskName = state.value.taskName
                val taskDescription = state.value.taskDescription
                val dueDate = state.value.dueDate
                val taskPriority = state.value.taskPriority

                if (taskName.isBlank() || dueDate.isBlank()) {
                    return
                }

                val task = Task(
                    priority = taskPriority,
                    dueDate = dueDate,
                    isActive = true,
                    taskName = taskName,
                    taskDescription = taskDescription
                )

                viewModelScope.launch {
                    dao.insertTask(task)
                }

                _state.update { it.copy(
                    dueDate = "",
                    isAddingTask = false,
                    taskName = "",
                    taskDescription = ""
                ) }
            }
            is TaskEvent.UpdateTask -> {
                val taskName = state.value.taskName

                if (taskName.isBlank()) {
                    return
                }

                viewModelScope.launch {
                    dao.updateTask(event.task)
                }

                _state.update { it.copy(
                    isActive = true,
                    isUpdatingTask = false,
                    taskName = "",
                    taskDescription = ""
                ) }
            }
            is TaskEvent.UpdateTaskStatus -> {
                _state.update { it.copy(
                    isActive = event.isActive,
                ) }
            }
            is TaskEvent.SetTaskDescription -> {
                _state.update { it.copy(
                    taskDescription = event.taskDescription
                ) }
            }
            is TaskEvent.SetTaskName -> {
                _state.update { it.copy(
                    taskName = event.taskName
                ) }
            }
            is TaskEvent.SetTaskPriority -> {
                _state.update { it.copy(
                    taskPriority = event.taskPriority
                ) }
            }
            is TaskEvent.SetDueDate -> {
                _state.update { it.copy(
                    dueDate = event.dueDate
                ) }
            }
            TaskEvent.ShowAddDialog -> {
                _state.update { it.copy(
                    isAddingTask = true
                ) }
            }
            TaskEvent.ShowUpdateDialog -> {
                _state.update { it.copy(
                    isUpdatingTask = true
                ) }
            }
            is TaskEvent.SortTasks -> {
                _sortType.value = event.sortType
            }
        }
    }
}