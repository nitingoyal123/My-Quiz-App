package com.example.quizapp.retrofit

import android.util.Log
import android.widget.Toast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    val baseURL = "http://192.168.1.5/quiz/"

    fun getRetrofitInstance() : Retrofit {
//        Toast.makeText(this,"ERROR !!!", Toast.LENGTH_SHORT).show()
        Log.i("TAGY","ERROR OCCURRED kk !!!")
        return Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}