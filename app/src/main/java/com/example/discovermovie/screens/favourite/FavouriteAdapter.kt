package com.example.discovermovie.screens.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.discovermovie.R
import com.example.discovermovie.data.localeDataBase.MovieEntity
import com.example.discovermovie.databinding.LayoutFavouiteMovieItemBinding

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private val favouriteMoviesList = ArrayList<MovieEntity>()

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LayoutFavouiteMovieItemBinding.bind(itemView)
        fun bind(data: MovieEntity) {
            binding.apply {
                Glide.with(itemView).load(
                    data.poster_path
                ).into(ivPosterSmall)
                tvMovieTitle.text = data.original_title
                tvMovieStatus.text = data.status
                tvMovieYearRealiseDate.text = data.release_date
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

    fun submitList(list: List<MovieEntity>) {
        favouriteMoviesList.clear()
        favouriteMoviesList.addAll(list.asReversed())
        notifyDataSetChanged()
    }
}