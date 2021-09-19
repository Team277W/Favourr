package com.example.favourr.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favourr.databinding.FragmentProfileBinding
import com.example.favourr.models.ProfileModel
import com.example.favourr.network.ApiInterface
import com.example.favourr.ui.connections.ProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileAdapter = ProfileFavourrAdapter(listOf())
        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.currentFavourrsRv.adapter = profileAdapter
        binding.currentFavourrsRv.layoutManager = linearLayoutManager

        val sharedPrefs = activity?.getSharedPreferences("LaunchPrefs", Context.MODE_PRIVATE)
        val apiInterface = ApiInterface.create().getAcceptedBounties(sharedPrefs?.getString("userId", "No user id") ?: "N/A")
        apiInterface.enqueue(object : Callback<ProfileModel> {
            override fun onResponse(call: Call<ProfileModel>?, response: Response<ProfileModel>?) {
                if (response?.isSuccessful == true) {
                    profileAdapter.setFavourrs(response.body().bounties)
                    binding.earningsBody.text = "$" + response.body().totalCash
                }
            }

            override fun onFailure(call: Call<ProfileModel>?, t: Throwable?) {
                // no-op
            }

        })
    }
}