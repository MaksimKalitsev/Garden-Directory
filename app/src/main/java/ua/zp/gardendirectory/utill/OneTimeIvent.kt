package ua.zp.gardendirectory.utill
import java.util.concurrent.atomic.AtomicBoolean

class OneTimeEvent<out T>(private val payload: T) {

    private var returned = AtomicBoolean(false)

    /**
     * Returns the payload only to the first caller. Subsequent calls get null.
     */
    fun getPayload(): T? {
        return if (returned.getAndSet(true)) {
            null
        } else {
            payload
        }
    }
}