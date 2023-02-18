package com.erkindilekci.countrylist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.erkindilekci.countrylist.databinding.FragmentCountryBinding
import com.erkindilekci.countrylist.util.downloadFromInternet
import com.erkindilekci.countrylist.util.placeHolderProgressBar
import com.erkindilekci.countrylist.viewmodel.CountryViewModel

class CountryFragment : Fragment() {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private var countryUuid = 0
    private lateinit var viewModel: CountryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner){
            it?.let {
                binding.countryName.text = it.countryName
                binding.countryCapitalName.text = it.countryCapital
                binding.countryCurrencyName.text = it.countryCurrency
                binding.countryRegionName.text = it.countryRegion
                binding.countryLanguageName.text = it.countryLanguage
                if (context != null){
                    it.countryFlag?.let { it1 ->
                        binding.countryFlagImageView.downloadFromInternet(
                            it1, placeHolderProgressBar(requireContext()))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}