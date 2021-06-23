package br.com.neillon.witweather.base

import android.content.Context

interface Navigator {
    fun navigateToFeature(context: Context?, feature: Features)
}