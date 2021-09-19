package com.example.favourr

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.favourr.ui.profile.ProfileFragment
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var connectionsClient: ConnectionsClient
    private val discoveredEndpoints = HashMap<String, Endpoint>()
    private val establishedConnections = HashMap<String, Endpoint?>()
    private val pendingConnections = HashMap<String, Endpoint>()
    private lateinit var sharedPrefs: SharedPreferences
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            val endpoint = Endpoint(endpointId, connectionInfo.endpointName)
            acceptConnection(endpoint)
            pendingConnections[endpointId] = endpoint
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (!result.status.isSuccess) {
                if (mainViewModel.state.value == MainViewModel.State.SEARCHING) startDiscovering()
            } else {
                val endpoint = pendingConnections.remove(endpointId)
                establishedConnections[endpointId] = endpoint
                mainViewModel.setState(MainViewModel.State.CONNECTED)
                val payload = Payload.fromBytes(USERNAME.toByteArray())
                send(payload, establishedConnections.keys)
            }
        }

        override fun onDisconnected(endpointId: String) {
            if (establishedConnections.containsKey(endpointId)) {
                establishedConnections.remove(endpointId)
                Toast.makeText(applicationContext, "Endpoint disconnected", Toast.LENGTH_SHORT).show()
                mainViewModel.setState(MainViewModel.State.SEARCHING)
            }
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            if (payload.type == Payload.Type.BYTES) {
                val id = String((payload.asBytes()!!), StandardCharsets.UTF_8)
                mainViewModel.setConnectionId(id)
            }
        }

        override fun onPayloadTransferUpdate(endpintId: String, update: PayloadTransferUpdate) {
            // no-op
        }
    }

    private fun send(payload: Payload, endpoints: Set<String>) {
        connectionsClient
            .sendPayload(ArrayList(endpoints), payload)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val createFavourr:Boolean = intent.getBooleanExtra("goProfile", false)
        if (createFavourr) {
            val newFragment: Fragment = ProfileFragment()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.state.observe(this, Observer {
            onStateChanged(it)
        })
        connectionsClient = Nearby.getConnectionsClient(this)

        sharedPrefs = getSharedPreferences("LaunchPrefs", Context.MODE_PRIVATE)
        val username = intent.extras?.getString("Username") ?: sharedPrefs.getString("Username", "No username")
        val name = intent.extras?.getString("Name") ?: sharedPrefs.getString("Name", "No Name") ?: ""
        val city = intent.extras?.getString("City") ?: sharedPrefs.getString("City", "No City") ?: ""
        mainViewModel.setName(name)
        mainViewModel.setCity(city)
    }

    override fun onStart() {
        super.onStart()
        if (!hasPermissions(this, requiredPerms)) {
            requestPermissions(
                requiredPerms.toTypedArray(),
                REQUEST_CODE_REQUIRED_PERMS
            )
        }
        mainViewModel.setState(MainViewModel.State.SEARCHING)
    }

    private fun hasPermissions(context: Context, perms: List<String>): Boolean {
        perms.forEach {
            if (ContextCompat.checkSelfPermission(
                    context,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_REQUIRED_PERMS) {
            grantResults.forEach {
                if (it == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Missing permission", Toast.LENGTH_LONG).show()
                    return
                }
            }
            recreate()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun startAdvertising() {

        val advertisingOptions = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_STAR)
        connectionsClient
            .startAdvertising(
                NAME,
                SERVICE_ID,
                connectionLifecycleCallback,
                advertisingOptions.build()
            )
    }

    private fun startDiscovering() {
        discoveredEndpoints.clear()
        val discoveryOptions = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_STAR)
        connectionsClient
            .startDiscovery(
                SERVICE_ID,
                object : EndpointDiscoveryCallback() {
                    override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                        if (SERVICE_ID == info.serviceId) {
                            val endpoint = Endpoint(endpointId, info.endpointName)
                            discoveredEndpoints[endpointId] = endpoint
                            connectionsClient.stopDiscovery()
                            connectToEndpoint(endpoint)
                        }
                    }

                    override fun onEndpointLost(endpointId: String) {
                        // no-op
                    }

                },
                discoveryOptions.build()
            )
    }

    private fun connectToEndpoint(endpoint: Endpoint) {
        connectionsClient
            .requestConnection(NAME, endpoint.id, connectionLifecycleCallback)
            .addOnFailureListener {
                if (mainViewModel.state.value == MainViewModel.State.SEARCHING) {
                    startDiscovering()
                }
            }
    }

    private fun acceptConnection(endpoint: Endpoint) {
        connectionsClient
            .acceptConnection(endpoint.id, payloadCallback)
            .addOnFailureListener { Log.w(TAG, "acceptConnection failed") }
    }

    private fun onStateChanged(state: MainViewModel.State) {
        when (state) {
            MainViewModel.State.SEARCHING -> {
                establishedConnections.values.forEach {
                    if (it != null) {
                        connectionsClient.disconnectFromEndpoint(it.id)
                    }
                }
                establishedConnections.clear()
                startAdvertising()
                startDiscovering()
            }
            MainViewModel.State.CONNECTED -> {
                connectionsClient.stopDiscovery()
                connectionsClient.stopAdvertising()
            }

        }
    }

    data class Endpoint(val id: String, val name: String)


    companion object {
        private val locationPerm =
            if (Build.VERSION.SDK_INT >= 29) Manifest.permission.ACCESS_FINE_LOCATION else Manifest.permission.ACCESS_COARSE_LOCATION
        private val requiredPerms = listOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            locationPerm,
        )
        private const val REQUEST_CODE_REQUIRED_PERMS = 1
        const val USERNAME = "TestUsername"
        const val NAME = "TestName"
        private const val SERVICE_ID = "com.team277.favourr.SERVICE_ID"
        private const val TAG = "com.team277.favourr.TAG"
    }
}