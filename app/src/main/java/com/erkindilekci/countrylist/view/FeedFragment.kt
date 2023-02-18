package com.erkindilekci.countrylist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.erkindilekci.countrylist.adapter.CountryAdapter
import com.erkindilekci.countrylist.databinding.FragmentFeedBinding
import com.erkindilekci.countrylist.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FeedViewModel
    private var countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        binding.countryList.layoutManager = LinearLayoutManager(context)
        binding.countryList.adapter = countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            binding.countryErrorTextView.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromApi()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner){
            it?.let {
                binding.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(it)
            }
        }
        viewModel.countryError.observe(viewLifecycleOwner){
            it?.let {
                if (it) {
                    // Error
                    binding.countryErrorTextView.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryList.visibility = View.GONE
                } else {
                    binding.countryErrorTextView.visibility = View.GONE
                    binding.countryList.visibility = View.VISIBLE
                    binding.countryList.visibility = View.VISIBLE
                }
            }
        }
        viewModel.countryLoading.observe(viewLifecycleOwner){
            it?.let {
                if (it) {
                    // Loading
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryList.visibility = View.GONE
                    binding.countryErrorTextView.visibility = View.GONE
                } else {
                    binding.countryLoading.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}