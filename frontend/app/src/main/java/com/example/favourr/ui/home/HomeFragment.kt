package com.example.favourr.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favourr.MainViewModel
import com.example.favourr.R
import com.example.favourr.databinding.FragmentHomeBinding
import com.example.favourr.ui.ActiveFavourrItemAdapter
import com.example.favourr.ui.AvailableFavourrItemAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mainViewModel.connectionId.observe(viewLifecycleOwner, Observer {
            // new connection
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val welcomeStr = getString(R.string.welcome, mainViewModel.name.value)
        val ssb = SpannableStringBuilder(welcomeStr)
        ssb.setSpan(StyleSpan(Typeface.BOLD), welcomeStr.indexOf('\n'), welcomeStr.length - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.welcomeBack.text = ssb

        val favourrs = listOf<Favourr>()
        val activeFavourrsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val activeFavourrsAdapter = ActiveFavourrItemAdapter(favourrs)
        binding.activeFavourrsRv.layoutManager = activeFavourrsLayoutManager
        binding.activeFavourrsRv.adapter = activeFavourrsAdapter
        val availableFavourrsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val availableFavourrsAdapter = AvailableFavourrItemAdapter(favourrs)
        binding.availableFavourrsRv.layoutManager = availableFavourrsLayoutManager
        binding.availableFavourrsRv.adapter = availableFavourrsAdapter
    }
}