package com.example.discovermovie.screens.details


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import com.example.discovermovie.databinding.CustomRateDialogBinding
import kotlin.math.max
import kotlin.math.min


class RateMovie(context: Context) : Dialog(context) {
    private lateinit var binding: CustomRateDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CustomRateDialogBinding.inflate(layoutInflater)
            Toast.makeText(context, "Text", Toast.LENGTH_SHORT).show()
            binding.movieRatingBar.setOnRatingBarChangeListener() { ratingBar: RatingBar, rating: Float, fromUser: Boolean ->

                if (fromUser) {
                    ratingBar.rating = max(min(rating, 5F), 1F)
                    Toast.makeText(context, (rating*2).toString(), Toast.LENGTH_SHORT).show()
                }
            }
       



        binding.ivMoviePoster.setOnClickListener {
            Toast.makeText(context, "Toast", Toast.LENGTH_SHORT).show()
        }





        setContentView(binding.root)
    }
}





