import logic.CommonTimer
import logic.TimerFactory
import org.koin.dsl.module

val commonModule
    get() = module {
        factory<CommonTimer> { TimerFactory.create() }
    }