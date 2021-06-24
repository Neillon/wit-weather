package br.com.neillon.location

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.neillon.location.databinding.ActivityStartBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*

class StartActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "StartActivity"
        private const val REQUEST_CHECK_SETTINGS: Int = 1

        fun newInstance(context: Context) = Intent(context, StartActivity::class.java)
    }

    private var _binding: ActivityStartBinding? = null
    private val binding by lazy { _binding!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createLocationRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CHECK_SETTINGS) {
            navigateToLocation()
        }
    }

    private fun createLocationRequest() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task
            .addOnSuccessListener { navigateToLocation() }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(
                            this@StartActivity,
                            REQUEST_CHECK_SETTINGS
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Log.e(TAG, "createLocationRequest: Error sending intent")
                    }
                }
            }
    }

    private fun navigateToLocation() {
        LocationActivity.newInstance(this).apply {
            startActivity(this)
        }
        finish()
    }
}