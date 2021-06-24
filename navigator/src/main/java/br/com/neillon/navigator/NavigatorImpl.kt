package br.com.neillon.navigator

import android.content.Context
import br.com.neillon.core.navigation.Features
import br.com.neillon.core.navigation.Navigator
import br.com.neillon.home.presentation.ui.HomeActivity
import br.com.neillon.location.ui.LocationActivity
import br.com.neillon.navigator.exception.FeatureNotFoundException

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