package com.example.discovermovie.screens.details

//import android.support.v7.graphics.Palette
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.discovermovie.databinding.FragmentDetailsBinding
import com.example.discovermovie.movieModels.details.Genre
import com.example.discovermovie.screens.home.HomeAdapter
import com.example.discovermovie.util.BASE_IMAGE_URL
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_BIG
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_SMALL
import com.example.discovermovie.util.Resource


class DetailsFragment : Fragment(), HomeAdapter.OnItemClickListener {
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val movieId = arguments?.getInt("MovieId") as Int
        detailViewModel.getMovieDetail(movieId)
        detailViewModel.getSimilarMovies(movieId)
        detailViewModel.getImages(movieId)
        adapter = HomeAdapter(this)
        videoAdapter = VideoAdapter()
        observeSimilarMovies()
        setUpVideoRecycler()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.movieDetailLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data!!.let {
                        it.apply {
                            binding.apply {
                                Glide.with(this@DetailsFragment)
                                    .load(BASE_IMAGE_URL + IMAGE_POSTER_SIZE_BIG + backdrop_path)
                                    .centerCrop()
                                    .into(ivPosterBig)



                                Glide.with(this@DetailsFragment)
                                    .load(BASE_IMAGE_URL + IMAGE_POSTER_SIZE_SMALL + poster_path)
                                    .into(ivPosterSmall)
                                tvMovieDateRelease.text = release_date
                                tvMovieTitle.text = original_title
                                tvMovieDateRelease.text = release_date
                                tvMovieRuntime.text = "$runtime min"
                                movieRating.rating = vote_average.toFloat()
                                tvMovieOverview.text = overview
                                tvMovieGenre.text = getGenre(genres)
                            }
                        }
                    }
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                else -> {}
            }


        }


    }

    private fun observeSimilarMovies() {
        binding.rvSimilarMovies.adapter = adapter
        detailViewModel.similarMoviesLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data.let {
                        adapter.submitList(it!!)
                    }
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                else -> {}
            }
        }
    }


    private fun setUpVideoRecycler() {
        binding.rvTrailersImages.adapter = videoAdapter
        detailViewModel.imagesLiveData.observe(viewLifecycleOwner) {
            videoAdapter.submitList(it)
        }
    }


    private fun getGenre(genreList: List<Genre>): String {
        var genre: String? = null
        for (i in genreList.indices) {
            genre += genreList[i].name + " ,"
        }
        return genre!!.removeSuffix(",").removePrefix(null.toString())
    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()

    }

    override fun onItemClick(movieId: Int) {
        TODO("Not yet implemented")
    }

//    fun backgroundFromImage() {
//
//        Glide.with(this@DetailsFragment)
//            .asBitmap()
//            .load("${BASE_IMAGE_URL + IMAGE_POSTER_SIZE_BIG + backdrop_path}")
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap>?
//                ) {
//                    val palette = Palette.from(resource).generate()
//                    val vibrant = palette.lightVibrantSwatch
//                    binding.detailLayout.setBackgroundColor(vibrant!!.rgb)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    TODO("Not yet implemented")
//                }
//
//            })
//    }


}