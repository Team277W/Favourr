package com.example.favourr.ui.home

import android.content.Context
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
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favourr.*
import com.example.favourr.databinding.FragmentHomeBinding
import com.example.favourr.models.CityModel
import com.example.favourr.models.ProfileModel
import com.example.favourr.network.ApiInterface
import com.example.favourr.ui.ActiveFavourrItemAdapter
import com.example.favourr.ui.AvailableFavourrItemAdapter
import com.example.favourr.ui.profile.ProfileFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.favourr.SwipeToDeleteCallback

import androidx.recyclerview.widget.ItemTouchHelper




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

        binding.profileButton.setOnClickListener {
            val newFragment: Fragment = ProfileFragment()
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val activeFavourrsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val activeFavourrsAdapter = ActiveFavourrItemAdapter(listOf())
        binding.activeFavourrsRv.layoutManager = activeFavourrsLayoutManager
        binding.activeFavourrsRv.adapter = activeFavourrsAdapter
        val availableFavourrsLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val availableFavourrsAdapter = AvailableFavourrItemAdapter(listOf())
        binding.availableFavourrsRv.layoutManager = availableFavourrsLayoutManager
        binding.availableFavourrsRv.adapter = availableFavourrsAdapter
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(availableFavourrsAdapter, activeFavourrsAdapter))
        itemTouchHelper.attachToRecyclerView(binding.availableFavourrsRv)

        mainViewModel.encounteredFavourrs.observe(viewLifecycleOwner, Observer {
            availableFavourrsAdapter.setFavourrs(it)
            availableFavourrsAdapter.notifyDataSetChanged()
        })

        val sharedPrefs = activity?.getSharedPreferences("LaunchPrefs", Context.MODE_PRIVATE)
        val userId = sharedPrefs?.getString("userId", "No user id") ?: "N/A"
        val apiInterface = ApiInterface.create().getAcceptedBounties(userId)
        apiInterface.enqueue(object : Callback<ProfileModel> {
            override fun onResponse(
                call: Call<ProfileModel>?,
                response: Response<ProfileModel>?
            ) {
                response?.body()?.let {
                    activeFavourrsAdapter.setFavourrs(it.bounties)
                }
            }

            override fun onFailure(call: Call<ProfileModel>?, t: Throwable?) {
                // no-op
            }
        })
    }
}