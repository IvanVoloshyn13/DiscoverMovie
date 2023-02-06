package com.example.discovermovie.screens.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.discovermovie.R
import com.example.discovermovie.databinding.LayoutMovieDiscoverItemBinding
import com.example.discovermovie.movieModels.simpleMovieModel.MovieItemModel
import com.example.discovermovie.util.BASE_IMAGE_URL
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_SMALL

class HomeAdapter(val listener: OnItemClickListener) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    val moviesList = ArrayList<MovieItemModel>()

    inner class HomeViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: LayoutMovieDiscoverItemBinding = LayoutMovieDiscoverItemBinding.bind(itemView)

        fun bind(data: MovieItemModel) {
            binding.apply {
                Glide.with(itemView.context)
                    .load("${BASE_IMAGE_URL + IMAGE_POSTER_SIZE_SMALL + data.poster_path}")
                    .placeholder(
                        R.drawable.ic_launcher_background
                    ).into(ivMoviePosterSmall)
                tvMovieTitle.text = data.title
                itemView.setOnClickListener {
                    val movieId = data.id
                    listener.onItemClick(movieId)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_movie_discover_item, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(moviesList[position])

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun submitList(list: List<MovieItemModel>) {
        moviesList.clear()
        Log.d("ONVIEWCREATED","Adapter")
        moviesList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(movieId: Int)
    }
}