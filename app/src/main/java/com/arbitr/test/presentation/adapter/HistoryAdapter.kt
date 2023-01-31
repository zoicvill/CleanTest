package com.arbitr.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbitr.test.R
import com.arbitr.test.data.network.model.Ratings
import com.arbitr.test.databinding.RatingItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class BinDiffUtil : DiffUtil.ItemCallback<Ratings>() {

    override fun areItemsTheSame(oldItem: Ratings, newItem: Ratings) =
        oldItem.image == newItem.image

    override fun areContentsTheSame(oldItem: Ratings, newItem: Ratings) =
        oldItem == newItem

}

class HistoryAdapter :
    ListAdapter<Ratings, HistoryAdapter.BinListViewHolder>(BinDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BinListViewHolder(
            RatingItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.rating_item,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: BinListViewHolder, position: Int) =
        holder.bind(itemPosition = position)

    inner class BinListViewHolder(private val binding: RatingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {
            val bin = getItem(itemPosition)
            Glide.with(binding.root)
                .load(bin.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView)
            binding.imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            binding.textRating.text = bin.title
        }
    }

}
