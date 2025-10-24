package base_viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Action : UiAction>(
    initialState: State
) : ViewModel() {
    // Текущее состояние (для чтения из UI)

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()



    // Одноразовые действия (навигация, тосты, диалоги)
    private val _action = MutableSharedFlow<Action>()
    val action: SharedFlow<Action> = _action.asSharedFlow()

    // Корутин-скоп для ViewModel
    protected val viewModelScope = CoroutineScope(Dispatchers.Main)

    // Обработчик событий от UI
    fun onEvent(event: Event) {
        viewModelScope.launch {
            handleEvent(event)
        }
    }

    // Обновление состояния
    protected fun updateState(reducer: State.() -> State) {
        _state.update(reducer)
    }

    // Отправка одноразового действия
    protected suspend fun sendAction(action: Action) {
        _action.emit(action)
    }

    // Абстрактный метод для обработки событий
    protected abstract suspend fun handleEvent(event: Event)

}