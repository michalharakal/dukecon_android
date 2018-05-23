package org.dukecon.domain.features.networking

interface NetworkUtils {

    val isWiFiConected: Boolean

    val isMobileNetworkConected: Boolean

    val isInternetConected: Boolean

    var isConnectedToCaptivePortal: Boolean

    val isOffline: Boolean
}
