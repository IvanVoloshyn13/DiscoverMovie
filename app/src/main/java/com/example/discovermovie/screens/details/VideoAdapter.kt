package com.example.discovermovie.screens.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.discovermovie.R
import com.example.discovermovie.data.movieModels.images.Backdrop
import com.example.discovermovie.databinding.LayoutTrailersItemBinding
import com.example.discovermovie.util.BASE_IMAGE_URL
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_BIG

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private val videosList = ArrayList<Backdrop>()


    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LayoutTrailersItemBinding.bind(itemView)

        fun bind(data: Backdrop) {
            binding.apply {
                Glide.with(itemView)
                    .load("${BASE_IMAGE_URL + IMAGE_POSTER_SIZE_BIG + data.file_path}")
                    .into(ivVideoImageView)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_trailers_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videosList[position])
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    fun submitList(list: List<Backdrop>) {
        videosList.clear()
        videosList.addAll(list)
        notifyDataSetChanged()
    }


}