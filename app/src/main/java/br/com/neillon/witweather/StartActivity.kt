package br.com.neillon.witweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.neillon.witweather.base.Features
import br.com.neillon.witweather.base.Navigator
import br.com.neillon.witweather.base.NavigatorImpl
import br.com.neillon.witweather.databinding.ActivityStartBinding
import kotlinx.coroutines.*

class StartActivity : AppCompatActivity() {

    private var _binding: ActivityStartBinding? = null
    private val binding by lazy { _binding!! }

    private val navigator: Navigator = NavigatorImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                delay(2_000L)
                withContext(Dispatchers.Main) {
                    navigator.navigateToFeature(this@StartActivity, Features.Location)
                    finish()
                }
            }
        }
    }

}