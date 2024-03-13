package com.example.tennistest.gui

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tennistest.Answer
import com.example.tennistest.R
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.jvm.Throws

class ResultFragment : Fragment() {

    private lateinit var btnSent: Button
    private lateinit var btnRemake: Button
    private lateinit var recyclerView: RecyclerView
    lateinit var arrayList: ArrayList<Answer>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.result_fragment, container, false)
        var args = this.arguments                                           // Получаем список ответов из QuestionFragment
        arrayList = args?.get("list") as ArrayList<Answer>

        var categories = resources.getStringArray(R.array.categories)      // Получаем список категорий из ресурсов
        init(view)                                                         // Запускаем инициализацию всех виджитов
        makeAnswers(categories, arrayList)                                 // Заполняем список ответов
        return view;
    }

    /**
     * Метод для инициализации виджитов
     */
    fun init(view: View){
        recyclerView = view.findViewById(R.id.result_recycler)
        btnSent = view.findViewById(R.id.sent)
        btnRemake = view.findViewById(R.id.remake_answer)
        btnRemake.setOnClickListener{
            var questionFragment:QuestionFragment = QuestionFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, questionFragment)
                ?.commit()
        }

        btnSent.setOnClickListener {
           var handler: NetworkHandler = NetworkHandler()
            handler.execute()
        }
    }

    /**
     * Класс для отправки запроса на сервер в фоновом потоке
     */
    inner class NetworkHandler(): AsyncTask<Boolean, Boolean, Boolean>() {

        @Throws(IOException::class)
        override fun doInBackground(vararg params: Boolean?): Boolean {
            val url = URL("http://bugz.su/core/api/core/new-player")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            val json = "{\n" +
                      "name\": \"string\",\n" +
                      "surName\": \"string\",\n" +
                      "phoneNumber\": \"string\",\n" +
                      "birthday\": \"2023-09-25T20:05:17.726Z\",\n" +
                      "isMale\": true,\n" +
                      "countryId\": 0,\n" +
                      "cityId\": 0,\n" +
                      "districtId\": 0,\n" +
                      "telegramId\": \"string\",\n" +
                      "surveyAnswer\": {\n" +
                        "experience:" + arrayList.get(0).number +
                        "forehand:" + arrayList.get(1).number +
                        "backhand:" + arrayList.get(2).number +
                        "slice:" + arrayList.get(3).number +
                        "serve:" + arrayList.get(4).number +
                        "net:" + arrayList.get(5).number +
                        "speed+" + arrayList.get(6).number +
                        "tournaments:" + arrayList.get(7).number +
                        "prizes:" + arrayList.get(8).number +
                     "}" +
                    "}"
            connection.outputStream.write(json.toByteArray())
            var data: Int = connection.inputStream.read()
            while (data != -1) {
                print(data.toChar())
                data = connection.inputStream.read()
            }
            return  true;
        }
    }


    /**
     * Метод для заполянения списка ответов
     */
    fun makeAnswers(categories: Array<String>, answers: ArrayList<Answer>){
        recyclerView.layoutManager = LinearLayoutManager(activity)           // Устанавливаем компоновщик для списка
        val layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration( context,       // Добавляем разделитель для элементов списка
            layoutManager.orientation))
        var resultAdapter = ResultAdapter(categories, answers);              // Передаем данные для списка
        recyclerView.adapter = resultAdapter;
    }
}