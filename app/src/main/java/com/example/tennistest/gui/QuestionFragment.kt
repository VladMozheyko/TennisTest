package com.example.tennistest.gui

import android.content.res.TypedArray
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tennistest.Answer
import com.example.tennistest.R
import com.example.tennistest.Utils


class QuestionFragment : Fragment() {
    private lateinit var txtExplain: ExpandableTextView
    private lateinit var txtQuestion: TextView
    private lateinit var imgBack: ImageView
    private lateinit var imgState: ImageView
    private var count: Int = 0
    private lateinit var questionAdapter: QuestionAdapter;
    private lateinit var recyclerView: RecyclerView
    private lateinit var view:View;
    private var answers : ArrayList<Answer> = arrayListOf()
    private lateinit var questionLayout: LinearLayout;
    private lateinit var questions: Array<String>
    private lateinit var args: Bundle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.question_fragment, container, false)
        questions = resources.getStringArray(R.array.questions)               // Получаем список вопросов из ресурсов
        init()                                                                // Инициализируем переменные
        makeAnswers(resources.getStringArray(Utils.answersLinks.get(0)))      // Заполняем вопросы с первого списка
        return view;
    }

    /**
     * Метод для инициализации
     */
    fun init(){
        txtQuestion = view.findViewById(R.id.txt_question) as TextView
        txtQuestion.setText(questions[count])
        imgBack = view.findViewById(R.id.back_arrow);
        imgState = view.findViewById(R.id.image_state);
        recyclerView = view.findViewById(R.id.question_list)
        txtExplain = view.findViewById(R.id.expand_tv)
        questionLayout = view.findViewById(R.id.question_fragment)
        // Если первый вопрос, то не видна кнопка назад
        if(count == 0){
            imgBack.isGone = true;
        }
        imgBack.setOnClickListener{                                       // Обработка нажатия на кнопку назад
            count--;
            answers.removeAt(count)
            fill(count)
        }
    }

    /**
     * Метод для заполнения вариантов ответов
     */
    fun makeAnswers(list: Array<String>){
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        questionAdapter = QuestionAdapter(list, ::onListItemClick);              // Передаем наполнение и функцию слушатель нажатия
        recyclerView.adapter = questionAdapter;
        imgState.setImageResource(Utils.states.get(count))                       // Устанавливаем индикатор прогресса
    }

    /**
     * Метод для заполнения выпадающего текстового поля
     */
    fun makeExpand(text: String){
        txtExplain.setText(text)
        txtExplain.isGone = false;
    }

    /**
     * Метод для заполнения виджетов фрагмента вопросов
     */
    fun fill(state: Int){
        // Если опрос запущен заново, убираем кнопку Назад
        if(count == 0){
            imgBack.isGone = true
        }
        else {
            imgBack.isGone = false
        }
        // Выводим текущий вопрос
        txtQuestion.setText(questions[state])
        // Выводм варианты ответа
        makeAnswers(resources.getStringArray(Utils.answersLinks.get(state)))
        // Выводи прогресс в опросе
        imgState.setImageResource(Utils.states.get(count))
        // В зависимости от состояния показываем подсказку(выпадающее текствое поле)
        if(state == 1){
            val text: String = getString(R.string.first_clue)
            makeExpand(text)
        }
        else if(state == 2){
            val text: String = getString(R.string.second_clue)
            makeExpand(text)
        }
        else{
            txtExplain.isGone = true
        }
    }

    /**
     * Метод для прослушивания нажатия на элемент списка
     */
    fun onListItemClick(position: Int, title: String) {
        answers.add(Answer(position, title))                           // Запоминаем ответ пользователя
        count++;                                                       // Учитываем ответ
        if(count == 9){                                                // Проверяем, если все вопросы пройдены, переходим к результатам
            args = Bundle()
            args.putSerializable("list", answers)
            var resultFragment =  ResultFragment()
            resultFragment.arguments = args
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, resultFragment)
                ?.commit()
        }
        else {                                                          // В противном случае заполняем новый вопрос
            fill(count)
        }
    }
}