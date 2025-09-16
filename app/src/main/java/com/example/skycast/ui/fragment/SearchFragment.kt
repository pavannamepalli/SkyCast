package com.example.skycast.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skycast.MainActivity
import com.example.skycast.R
import com.example.skycast.databinding.FragmentSearchBinding
import com.example.skycast.ui.adapter.LocalistListAdapter
import com.example.skycast.ui.adapter.OnItemClickListener
import com.example.skycast.ui.viewmodel.SearchViewModel
import com.example.skycast.utils.NoInternetDialogFragment
import com.example.skycast.utils.Resource
import com.example.skycast.utils.Utils
import com.example.skycast.viewmodelfactory.SearchViewModelFactory

class SearchFragment : Fragment(),OnItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.searchList.layoutManager=LinearLayoutManager(this.context)

        viewModel = ViewModelProvider(
            this,
            SearchViewModelFactory(this.requireActivity().applicationContext)
        ).get(SearchViewModel::class.java)

        binding.searchCities.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.getCitiesList(s)

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        viewModel.locationListLiveData.observe(viewLifecycleOwner){ resource ->
            when(resource){
                is Resource.Success -> {
                    val locationListData = resource.data
                    val adapter = LocalistListAdapter(locationListData,this)
                    binding.searchList.adapter=adapter
                }

                is Resource.Error -> {
                    val errorMessage = resource.message
                    if (errorMessage == "No internet connection") {
                        val dialogFragment = NoInternetDialogFragment.newInstance()
                        activity?.let {
                            dialogFragment.show(
                                it.supportFragmentManager,
                                "NoInternetDialog"
                            )
                        }
                    } else {
                        Utils().showToast(context, errorMessage)
                    }
                }
                is Resource.Loading -> {
                }
            }

        }

        return binding.root
    }

    override fun onItemClicked(key: String, localizedName: String) {
        val fragmenthome = HomeFragment()
        val args = Bundle()
        args.putString("key", key)
        args.putString("localizedName", localizedName)
        fragmenthome.arguments = args
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentView, fragmenthome)
            .addToBackStack(null)
            .commit()

        (activity as MainActivity).updateSelectedItem(R.id.navigation_home)
    }
}