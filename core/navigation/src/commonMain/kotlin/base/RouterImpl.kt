package base

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

internal class RouterImpl(
    private val logger: NavigationLogger
) : Router {

    private val _backstack = MutableStateFlow<List<Any>>(listOf())
    private val _screenTransitions = mutableMapOf<Any, TransitionConfig>()

    private val _nestedGraphs = mutableMapOf<String, MutableStateFlow<List<Any>>>()
    private val _sheets = MutableSharedFlow<Any>()
    internal val sheets: SharedFlow<Any> get() = _sheets.asSharedFlow()


    override val currentBackstack: StateFlow<List<Any>>
        get() = _backstack.asStateFlow()

    override fun setInitialScreen(screen: Any) {
        if (_backstack.value.isEmpty())
        _backstack.value = listOf(screen)
    }

    override fun navigateTo(screen: Any, transition: TransitionConfig?) {
        registerTransition(screen, transition)
        _backstack.update { it + screen }
        logNavigation("navigateTo", screen)

    }

    override fun replaceCurrent(screen: Any, transition: TransitionConfig?) {
        registerTransition(screen, transition)
        _backstack.update { it.dropLast(1) + screen }
        logNavigation("replaceCurrent", screen)
    }

    override fun clearStack(newScreen: Any) {
        _backstack.update { listOf(newScreen) }
        logNavigation("clearStack", newScreen)
    }

    override fun navigateBack() {
        _backstack.update { if (it.size > 1) it.dropLast(1) else it }
        logNavigation("navigateBack", _backstack.value.lastOrNull())
    }

    override fun popTo(screenClass: KClass<*>) {
        _backstack.update { stack ->
            stack.takeLastWhile { it::class != screenClass }.let {
                it.ifEmpty { stack }
            }
        }
        logNavigation("popTo", _backstack.value.lastOrNull())
    }

    override fun showBottomSheet(sheet: Any) {
        _sheets.tryEmit(sheet)
        logNavigation("showBottomSheet", sheet)
    }

    override fun registerNestedGraph(key: String, initialScreen: Any) {
        _nestedGraphs[key] = MutableStateFlow(listOf(initialScreen))
    }

    override fun navigateInGraph(key: String, screen: Any) {
        _nestedGraphs[key]?.update { it + screen }
            ?: error("Graph $key not registered")
        logNavigation("navigateInGraph[$key]", screen)
    }

    override fun getCurrentScreen(): Any = _backstack.value.last()

    override fun getTransitionForScreen(screen: Any): TransitionConfig {
        return _screenTransitions[screen] ?: TransitionConfig.FADE
    }

    internal fun getNestedGraph(key: String): StateFlow<List<Any>>? {
        return _nestedGraphs[key]?.asStateFlow()
    }

    private fun registerTransition(screen: Any, transition: TransitionConfig?) {
        transition?.let { _screenTransitions[screen] = it }
    }

    private fun logNavigation(action: String, screen: Any?) {
        logger.log("$action: ${screen?.let { it::class.simpleName } ?: "null"}")
    }

}