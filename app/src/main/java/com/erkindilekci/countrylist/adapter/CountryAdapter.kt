package com.erkindilekci.countrylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.erkindilekci.countrylist.databinding.CountryRowBinding
import com.erkindilekci.countrylist.model.Country
import com.erkindilekci.countrylist.util.downloadFromInternet
import com.erkindilekci.countrylist.util.placeHolderProgressBar
import com.erkindilekci.countrylist.view.FeedFragmentDirections

class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryHolder>(){
    class CountryHolder(val binding: CountryRowBinding): RecyclerView.ViewHolder(binding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val binding = CountryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.binding.nameText.text = countryList[position].countryName
        holder.binding.regionText.text = countryList[position].countryRegion

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryUuid = countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        countryList[position].countryFlag?.let {
            holder.binding.imageView.downloadFromInternet(
                it, placeHolderProgressBar(holder.itemView.context)
            )
        }
    }

    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}