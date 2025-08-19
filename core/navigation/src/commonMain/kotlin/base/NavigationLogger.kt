package base

interface NavigationLogger {
    fun log(message: String)
    fun enable()
    fun disable()
    fun isEnabled(): Boolean
}