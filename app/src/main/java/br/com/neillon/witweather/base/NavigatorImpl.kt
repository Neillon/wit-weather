package br.com.neillon.witweather.base

import android.content.Context
import br.com.neillon.witweather.features.home.HomeActivity
import br.com.neillon.witweather.features.location.LocationActivity

class NavigatorImpl : Navigator {

    companion object {
        private const val FEATURE_NOT_FOUND_ERROR_MESSAGE = "Feature not found"
    }

    override fun navigateToFeature(context: Context?, feature: Features) {
        context?.run {
            when (feature) {
                is Features.Home -> {
                    HomeActivity.newInstance(this).also { intent ->
                        startActivity(intent)
                    }
                }
                Features.Location -> {
                    LocationActivity.newInstance(this).also { intent ->
                        startActivity(intent)
                    }
                }
                else -> throw FeatureNotFoundException(FEATURE_NOT_FOUND_ERROR_MESSAGE)
            }
        }
    }
}