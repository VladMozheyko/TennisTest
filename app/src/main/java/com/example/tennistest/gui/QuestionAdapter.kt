package com.example.tennistest.gui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tennistest.R

class QuestionAdapter(private val questions: Array<String>,
                      private val onItemClicked: (position: Int, title: String) -> Unit)
    : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>(){

    var onItemClick: (() -> Unit)? = null

    class QuestionViewHolder(itemView: View, )
        : RecyclerView.ViewHolder(itemView){
       val questionBtn: Button = itemView.findViewById(R.id.btn_question)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_item, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.questionBtn.setText(questions[position])
        holder.questionBtn.setOnClickListener{
            onItemClicked(position, holder.questionBtn.text.toString());
        }

    }

    override fun getItemCount(): Int {
        return questions.size
    }

}