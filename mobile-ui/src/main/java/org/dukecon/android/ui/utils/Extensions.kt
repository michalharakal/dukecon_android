package org.dukecon.android.ui.utils

/** Convenience for callbacks/listeners whose return value indicates an event was consumed. */
inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}
