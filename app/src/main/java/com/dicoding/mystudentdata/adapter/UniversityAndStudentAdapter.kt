package com.dicoding.mystudentdata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mystudentdata.database.UniveristyAndStudent
import com.dicoding.mystudentdata.databinding.ItemStudentBinding

class UniveristyAndStudentAdapter :
    ListAdapter<UniveristyAndStudent, UniveristyAndStudentAdapter.WordViewHolder>(
        WordsComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WordViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UniveristyAndStudent) {
            val arrayName = arrayListOf<String>()
            data.student.forEach {
                arrayName.add(it.name)
            }
            binding.tvItemName.text = arrayName.joinToString(separator = ", ")
            binding.tvItemUniversity.text = data.university.name
            binding.tvItemUniversity.visibility = View.VISIBLE
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<UniveristyAndStudent>() {
        override fun areItemsTheSame(oldItem: UniveristyAndStudent, newItem: UniveristyAndStudent): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UniveristyAndStudent, newItem: UniveristyAndStudent): Boolean {
            return oldItem.university.name == newItem.university.name
        }
    }
}