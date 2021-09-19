package com.example.favourr.ui.localFavourrs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.favourr.databinding.FragmentLocalFavourrsBinding

class LocalFavourrsFragment : Fragment() {

    private lateinit var localFavourrsViewModel: LocalFavourrsViewModel
    private lateinit var binding: FragmentLocalFavourrsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        localFavourrsViewModel = ViewModelProvider(this).get(LocalFavourrsViewModel::class.java)
        binding = FragmentLocalFavourrsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}