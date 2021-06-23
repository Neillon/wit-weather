package br.com.neillon.witweather.features.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.neillon.witweather.base.Features
import br.com.neillon.witweather.base.GeoLocation
import br.com.neillon.witweather.base.Navigator
import br.com.neillon.witweather.base.NavigatorImpl
import br.com.neillon.witweather.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, LocationActivity::class.java)
    }

    private var _binding: ActivityLocationBinding? = null
    private val binding by lazy { _binding!! }

    private val navigator: Navigator = NavigatorImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        setupGivePermissionButton()
    }

    private fun setupGivePermissionButton() {
        binding.buttonGivePermission.setOnClickListener {
            val currentLocation = GeoLocation(0.0, 0.0)
            navigator.navigateToFeature(this, Features.Home(currentLocation))
        }
    }
}