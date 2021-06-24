package br.com.neillon.witweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.neillon.core.navigation.Features
import br.com.neillon.core.navigation.Navigator
import br.com.neillon.witweather.databinding.ActivityMainBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private val REQUEST_CHECK_SETTINGS: Int = 1

    private var _binding: ActivityMainBinding? = null
    private val binding by lazy { _binding!! }

    private val navigator: Navigator =
        br.com.neillon.navigator.NavigatorImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator.navigateToFeature(this, Features.Location)
        finish()
    }

}