package com.example.myapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.database.Repos
import com.example.myapplication.databinding.ListSingleItemBinding

class ListAdapter(
    private val onCardClick: OnCardClick,
    private val repositoryList: MutableList<Repos>,
    private val context: Context
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = repositoryList[position]
        holder.bind(items)
        holder.itemView.setOnClickListener {
            onCardClick.onClick(items)
        }
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    class ViewHolder(private val binding: ListSingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Repos) {
            Glide.with(itemView)
                .load(items.image)
                .centerCrop()
                .into(binding.imageView)

            binding.titleTv.text = "${items?.login}-${items.name}"
            binding.descriptionTv.text = "${items.description}"
            binding.langTv.text = "${items.language}"
        }

    }

    interface OnCardClick {
        fun onClick(items: Repos)
    }
}