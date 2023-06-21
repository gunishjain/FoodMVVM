package com.example.foodmvvm.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodmvvm.db.MealDatabase
import com.example.foodmvvm.pojo.*
import com.example.foodmvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    val mealDatabase: MealDatabase
) :ViewModel() {

    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularMealLiveData=MutableLiveData<List<MealsByCategory>>()
    private var categoryListLiveData=MutableLiveData<List<Category>>()
    private var favouriteMealLiveData=mealDatabase.mealDao().getAllMeals()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                if(response.body()!=null){
                    val randomMeal : Meal =response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal

                } else {
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment",t.message.toString())
            }

        })
    }

    fun getPopularMeals(){
        RetrofitInstance.api.getPopularMeals("seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if(response.body()!=null){
                    popularMealLiveData.value=response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("Home Fragment",t.message.toString())
            }

        })
    }

    fun getCategoryList(){
        RetrofitInstance.api.getCategoryList().enqueue(object: Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body().let {categoryMeal->
                    categoryListLiveData.postValue(categoryMeal!!.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Home Fragment",t.message.toString())
            }
        })
    }


    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularMealLiveData() : LiveData<List<MealsByCategory>>{
        return popularMealLiveData
    }

    fun observeCategoryListLiveData() : LiveData<List<Category>>{
        return categoryListLiveData
    }

    fun observeFavouriteMealListLiveData() : LiveData<List<Meal>>{
        return favouriteMealLiveData
    }


}