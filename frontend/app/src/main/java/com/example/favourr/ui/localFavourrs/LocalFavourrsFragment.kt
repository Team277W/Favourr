package com.example.favourr.ui.localFavourrs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favourr.SwipeToDeleteCallback
import com.example.favourr.databinding.FragmentLocalFavourrsBinding
import com.example.favourr.models.CityModel
import com.example.favourr.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val localFavourrsAdapter = LocalFavourrsAdapter(listOf())
        binding.localFavourrsRv.layoutManager = linearLayoutManager
        binding.localFavourrsRv.adapter = localFavourrsAdapter
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(localFavourrsAdapter, requireContext()))
        itemTouchHelper.attachToRecyclerView(binding.localFavourrsRv)

        val apiInterface = ApiInterface.create().getCityFavourrs("waterloo")
        apiInterface.enqueue(object : Callback<CityModel> {
            override fun onResponse(
                call: Call<CityModel>?,
                response: Response<CityModel>?
            ) {
                response?.body()?.let {
                    localFavourrsAdapter.setFavourrs(it.bounties)
                    val city = if (it.bounties.isNotEmpty()) it.bounties[0].city.capitalize() else "Waterloo"
                    binding.localFavourrsTitle.text = "Favourrs in " + city
                }
            }

            override fun onFailure(call: Call<CityModel>?, t: Throwable?) {
                // no-op
            }
        })
    }
}