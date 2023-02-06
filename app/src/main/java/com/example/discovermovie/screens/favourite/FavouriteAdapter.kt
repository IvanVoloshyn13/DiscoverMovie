package com.example.discovermovie.screens.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.discovermovie.R
import com.example.discovermovie.databinding.LayoutFavouiteMovieItemBinding
import com.example.discovermovie.movieModels.DatabaseMovieModel
import com.example.discovermovie.util.BASE_IMAGE_URL
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_SMALL

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private val favouriteMoviesList = ArrayList<DatabaseMovieModel>()

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LayoutFavouiteMovieItemBinding.bind(itemView)
        fun bind(data: DatabaseMovieModel) {
            binding.apply {
                Glide.with(itemView).load(
                    BASE_IMAGE_URL + IMAGE_POSTER_SIZE_SMALL + data.poster_path
                ).into(ivPosterSmall)
                tvMovieTitle.text = data.original_title
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_favouite_movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(favouriteMoviesList[position])
    }

    override fun getItemCount(): Int {
        return favouriteMoviesList.size
    }

    fun submitList(list: List<DatabaseMovieModel>) {
        favouriteMoviesList.clear()
        favouriteMoviesList.addAll(list)
        notifyDataSetChanged()
    }
}