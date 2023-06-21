package com.example.foodmvvm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodmvvm.activities.MainActivity
import com.example.foodmvvm.adapters.FavouriteMealsAdapter
import com.example.foodmvvm.databinding.FragmentFavouritesBinding
import com.example.foodmvvm.viewmodels.HomeViewModel

class FavouritesFragment : Fragment() {

    private lateinit var binding:FragmentFavouritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favouriteMealsAdapter: FavouriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavouritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareFavRecyclerView()
        observeFavouriteMeals()

    }

    private fun prepareFavRecyclerView() {
        favouriteMealsAdapter= FavouriteMealsAdapter()
        binding.rvFav.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=favouriteMealsAdapter
        }
    }

    private fun observeFavouriteMeals() {
        viewModel.observeFavouriteMealListLiveData().observe(viewLifecycleOwner, Observer {meals->
                favouriteMealsAdapter.differ.submitList(meals)
        })
    }


}