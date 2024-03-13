package com.example.tennistest.gui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tennistest.Answer
import com.example.tennistest.R

class ResultAdapter(private val categories: Array<String>, private val answers: ArrayList<Answer>,):
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>(){

    class ResultViewHolder(itemView: View, )
        : RecyclerView.ViewHolder(itemView){
        val txtCategory: TextView = itemView.findViewById(R.id.category)
        val txtAnswer: TextView = itemView.findViewById(R.id.answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.result_item, parent, false)
        return ResultViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.txtCategory.setText(categories[position])
        holder.txtAnswer.setText(answers[position].title)

    }

    override fun getItemCount(): Int {
        return answers.size
    }
}