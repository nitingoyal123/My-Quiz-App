package com.example.quizapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.model.Question
import com.example.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var quizViewModel : QuizViewModel
    lateinit var questionsList : List<Question>

    companion object{
        var result = 0
        var totalQuestions = 0
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Getting the response
        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        // Display the first question
        GlobalScope.launch(Dispatchers.Main) {
            try {
                quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                    Log.i("TAGY","HELLLLLO ${it.size}")
                    if (it.size>0) {
                        questionsList = it

                        binding.apply {
                            txtQues.text = questionsList[0].question
                            radio1.text = questionsList[0].option1
                            radio2.text = questionsList[0].option2
                            radio3.text = questionsList[0].option3
                            radio4.text = questionsList[0].option4
                        }

//                    Log.i("TAGY","First Question is ${questionsList[0].question}")
//                    Toast.makeText(this@MainActivity,"Question is ${questionsList[0].question}",Toast.LENGTH_SHORT).show()
                    }
                })
            } catch(e : IllegalArgumentException) {
                Toast.makeText(this@MainActivity,"ERROR !!!",Toast.LENGTH_SHORT).show()
            }
        }

        var i = 0
        binding.btnNext.setOnClickListener {

            binding.apply {

                val selectedOption = radioGroup.checkedRadioButtonId

                if (selectedOption != null) {
                    val radButton = findViewById<View>(selectedOption) as RadioButton
                    questionsList.let {

                        if (i < it.size!!.minus(1)) {
                            // no. of questions
                            totalQuestions = it.size

                            //check if the selected is one correct or not
                            if (radButton.text.toString().equals(it[i].answer)) {
                                result++
                                Toast.makeText(this@MainActivity,"Right Answer !!!",Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@MainActivity,"Wrong Answer !!!",Toast.LENGTH_SHORT).show()
                            }

                            //displaying the next ques
                            txtQues.text = questionsList[i+1].question
                            radio1.text = questionsList[i+1].option1
                            radio2.text = questionsList[i+1].option2
                            radio3.text = questionsList[i+1].option3
                            radio4.text = questionsList[i+1].option4

                            if (i == it.size.minus(1)) {
                                btnNext.text = "Finish"
                            }
                            radioGroup.clearCheck()
                            i++
                        } else {
//                            if (i==it.size) {
//                                //check if the selected is one correct or not
//                                if (radButton.text.toString().equals(it[i-1].answer)) {
//                                    result++
//                                    Toast.makeText(this@MainActivity,"Right Answer !!!",Toast.LENGTH_SHORT).show()
//                                } else {
//                                    Toast.makeText(this@MainActivity,"Wrong Answer !!!",Toast.LENGTH_SHORT).show()
//                                }
//                                i++
//                            } else {
//                                //TODO
//                            }
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity,"Select one option to continue...",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}