package com.app.pranavfreshworks.network

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No internet access.Please check your internet connection and try again"
}