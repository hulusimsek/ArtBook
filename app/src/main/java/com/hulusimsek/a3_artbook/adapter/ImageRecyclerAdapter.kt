package com.hulusimsek.a3_artbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.hulusimsek.a3_artbook.adapter.ArtRecyclerAdapter.ArtViewHolder
import com.hulusimsek.a3_artbook.databinding.ArtRowBinding
import com.hulusimsek.a3_artbook.databinding.ImageRowBinding
import com.hulusimsek.a3_artbook.roomdb.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    private val glide: RequestManager
)
    : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {



    class ImageViewHolder(val binding: ImageRowBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener : ((String) -> Unit) ? = null


    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)
    var images: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]
        glide.load(url).into(holder.binding.singleArtImageView)
        holder.itemView.apply {
            setOnClickListener{
                onItemClickListener?.let {
                    it(url)
                }
            }
        }
    }

}