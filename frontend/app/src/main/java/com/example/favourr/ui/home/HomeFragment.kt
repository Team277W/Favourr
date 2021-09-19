package com.example.favourr.ui.home

import android.content.Intent
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
import com.example.favourr.*
import com.example.favourr.databinding.FragmentHomeBinding
import com.example.favourr.models.CityModel
import com.example.favourr.network.ApiInterface
import com.example.favourr.ui.ActiveFavourrItemAdapter
import com.example.favourr.ui.AvailableFavourrItemAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        ssb.setSpan(StyleSpan(Typeface.BOLD), welcomeStr.indexOf('\n'), welcomeStr.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.welcomeBack.text = ssb

        binding.favourButton.setOnClickListener {
            val intent = Intent(requireContext(), CreateFavourActivity::class.java)
            startActivity(intent)
        }

        val activeFavourrsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val activeFavourrsAdapter = ActiveFavourrItemAdapter(listOf())
        binding.activeFavourrsRv.layoutManager = activeFavourrsLayoutManager
        binding.activeFavourrsRv.adapter = activeFavourrsAdapter
        val availableFavourrsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val availableFavourrsAdapter = AvailableFavourrItemAdapter(listOf())
        binding.availableFavourrsRv.layoutManager = availableFavourrsLayoutManager
        binding.availableFavourrsRv.adapter = availableFavourrsAdapter

        // TODO: val apiInterface = ApiInterface.create().getCityFavourrs(mainViewModel.city.value ?: "")
        val apiInterface = ApiInterface.create().getCityFavourrs("waterloo")
        apiInterface.enqueue(object : Callback<CityModel> {
            override fun onResponse(
                call: Call<CityModel>?,
                response: Response<CityModel>?
            ) {
                response?.body()?.let {
                    activeFavourrsAdapter.setFavourrs(it.bounties)
                    availableFavourrsAdapter.setFavourrs(it.bounties)
                }
            }

            override fun onFailure(call: Call<CityModel>?, t: Throwable?) {
                // no-op
            }
        })
    }
}