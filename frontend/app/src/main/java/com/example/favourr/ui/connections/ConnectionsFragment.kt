package com.example.favourr.ui.connections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.favourr.R
import com.google.android.gms.nearby.connection.ConnectionsClient

class ConnectionsFragment : Fragment() {

    private lateinit var connectionsViewModel: ConnectionsViewModel
    private lateinit var connectionsClient: ConnectionsClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        connectionsViewModel =
            ViewModelProvider(this).get(ConnectionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        connectionsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }


}