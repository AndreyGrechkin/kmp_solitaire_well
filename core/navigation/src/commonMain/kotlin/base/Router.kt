package base

import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KClass


interface Router {
    val currentBackstack: StateFlow<List<Any>>
    fun navigateTo(screen: Any, transition: TransitionConfig? = null)
    fun navigateBack()
    fun replaceCurrent(screen: Any, transition: TransitionConfig? = null)
    fun clearStack(newScreen: Any)
    fun registerNestedGraph(key: String, initialScreen: Any)
    fun navigateInGraph(key: String, screen: Any)
    fun getCurrentScreen(): Any
    fun setInitialScreen(screen: Any)
    fun showBottomSheet(sheet: Any)
    fun popTo(screenClass: KClass<*>)
    fun getTransitionForScreen(screen: Any): TransitionConfig
}