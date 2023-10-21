package com.example.quizapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.model.QuestionList
import com.example.quizapp.retrofit.QuestionsAPI
import com.example.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.create

class QuestionRepository {

    var questionsAPI : QuestionsAPI

    init {
        questionsAPI = RetrofitInstance().getRetrofitInstance().create(QuestionsAPI::class.java)
    }

    fun getQuestionsFromAPI() : LiveData<QuestionList>  {
        //live data
        var data = MutableLiveData<QuestionList>()
        var questionList : QuestionList

        GlobalScope.launch(Dispatchers.IO) {
            val response = questionsAPI.getQuestions()

            if (response != null) {
                //saving the data into array list
                questionList = response.body()!!

                data.postValue(questionList)

            }
        }
        return data
    }

}