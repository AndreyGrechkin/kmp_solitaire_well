package base

internal class NavigationLoggerImpl: NavigationLogger {
    private var enabled = false

    override fun enable() { enabled = true }
    override fun disable() { enabled = false }
    override fun isEnabled(): Boolean = enabled

    override fun log(message: String) {
        if (enabled) println("[NAV] $message")
    }
}