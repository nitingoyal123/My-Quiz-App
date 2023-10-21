package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.QuestionList
import com.example.quizapp.repository.QuestionRepository

class QuizViewModel : ViewModel() {

    var repository = QuestionRepository()

    var questionsLiveData : LiveData<QuestionList>

    init{
        questionsLiveData = repository.getQuestionsFromAPI()
    }

    fun getQuestionsFromLiveData() : LiveData<QuestionList> {
        return questionsLiveData
    }

}