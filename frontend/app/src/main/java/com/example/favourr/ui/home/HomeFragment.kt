package com.example.favourr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.favourr.R
import com.google.android.gms.nearby.connection.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var connectionsClient: ConnectionsClient

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    private fun startAdvertising() {
        val localName = "TestUsername"

        val advertisingOptions = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_STAR)
    }

    companion object {
        private const val SERVICE_ID = "com.team277.favourr.SERVICE_ID"
        private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(p0: String, p1: ConnectionInfo) {
                TODO("Not yet implemented")
            }

            override fun onConnectionResult(p0: String, p1: ConnectionResolution) {
                TODO("Not yet implemented")
            }

            override fun onDisconnected(p0: String) {
                TODO("Not yet implemented")
            }

        }
    }
}