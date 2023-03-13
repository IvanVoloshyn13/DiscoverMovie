package com.example.discovermovie.screens.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.discovermovie.databinding.CustomRateDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.math.max
import kotlin.math.min

@AndroidEntryPoint
class RateMovie(val movieId: Int) : DialogFragment() {

    private lateinit var binding: CustomRateDialogBinding
    private var movieRating = 0.0
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomRateDialogBinding.inflate(layoutInflater)
        val movieImage = arguments?.getString("IMAGE") as String


        Glide.with(this@RateMovie).load(movieImage).into(binding.ivMoviePoster)
        binding.movieRatingBar.setOnRatingBarChangeListener() { ratingBar: RatingBar, rating: Float, fromUser: Boolean ->
            if (fromUser) {
                ratingBar.rating = max(min(rating, 5F), 1F)
                movieRating = rating.toDouble() * 2
                Toast.makeText(context, (rating * 2).toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.bttClose.setOnClickListener {
            dismiss()
        }

        binding.tvRate.setOnClickListener {
            if (movieRating > 0) {
                detailViewModel.postRating(movieId, movieRating)
                Toast.makeText(
                    this@RateMovie.context,
                    movieRating.toString(),
                    Toast.LENGTH_SHORT
                )
                    .show()
                runBlocking {
                    delay(2000L)
                    dismiss()
                }

            }
        }
        return binding.root
    }

}





