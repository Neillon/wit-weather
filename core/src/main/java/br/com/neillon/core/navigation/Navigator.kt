package br.com.neillon.core.navigation

import android.content.Context

interface Navigator {
    fun navigateToFeature(context: Context?, feature: Features)
}