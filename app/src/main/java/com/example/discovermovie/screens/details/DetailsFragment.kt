package com.example.discovermovie.screens.details


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.discovermovie.MoviesApplication
import com.example.discovermovie.R
import com.example.discovermovie.databinding.FragmentDetailsBinding
import com.example.discovermovie.movieModels.DatabaseMovieModel
import com.example.discovermovie.movieModels.details.Genre
import com.example.discovermovie.repository.DatabaseMovieRepository
import com.example.discovermovie.screens.favourite.FavouriteViewModel
import com.example.discovermovie.screens.favourite.FavouriteViewModelProviderFactory
import com.example.discovermovie.screens.home.HomeAdapter
import com.example.discovermovie.util.BASE_IMAGE_URL
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_BIG
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_SMALL
import com.example.discovermovie.util.Resource
import kotlin.properties.Delegates


class DetailsFragment : Fragment(), HomeAdapter.OnItemClickListener {
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var adapter: HomeAdapter
    private var movieId by Delegates.notNull<Int>()

    private lateinit var dbRepository: DatabaseMovieRepository
    private lateinit var favouriteViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        adapter = HomeAdapter(this)
        movieId = arguments?.getInt("MovieId") as Int
        val movieDb = MoviesApplication.movieDatabase
        dbRepository = DatabaseMovieRepository(movieDb)
        favouriteViewModel = viewModels<FavouriteViewModel> {
            FavouriteViewModelProviderFactory(dbRepository = dbRepository)
        }.value
        detailViewModel.apply {
            getMovieDetail(movieId)
            getSimilarMovies(movieId)
            getImages(movieId)
        }
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
                    response.data!!.let { detail ->
                        detail.apply {
                            binding.apply {
                                Glide.with(this@DetailsFragment)
                                    .load(BASE_IMAGE_URL + IMAGE_POSTER_SIZE_BIG + backdrop_path)
                                    .centerCrop()
                                    .into(ivPosterBig)

                                // backgroundFromImage(backdrop_path)

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

                            binding.bttAddToFavourite.setOnClickListener {
                                val movie = DatabaseMovieModel(
                                    id = null,
                                    movieId = detail.movieId,
                                    original_title = detail.original_title,
                                    poster_path = BASE_IMAGE_URL + IMAGE_POSTER_SIZE_BIG + poster_path,
                                    status = status,
                                    release_date = release_date
                                )
                                Log.d("DATABASE", movie.original_title)
                                favouriteViewModel.addToFavourite(movie)
                            }
                        }
                    }
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                else -> {}
            }
        }

        isFavourite(movieId) {
            when (it) {
                true -> binding.bttAddToFavourite.setImageResource(R.drawable.ic_is_favorite)
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
                        if (it!!.isEmpty()) {
                            binding.tvSimilarMovies.visibility = View.GONE
                            binding.rvSimilarMovies.visibility == View.GONE
                        } else
                            adapter.submitList(it)
                    }
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                else -> {}
            }
        }
    }


    private fun setUpVideoRecycler() {
        videoAdapter = VideoAdapter()
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

    private fun isFavourite(movieId: Int, callback: (Boolean) -> Unit) {
        var isFavourite = false
        var favouriteMoviesList: List<DatabaseMovieModel>?
        favouriteViewModel.getFavouriteMovies().observe(viewLifecycleOwner) {
            favouriteMoviesList = it
            if (favouriteMoviesList != null) {
                for (elements in favouriteMoviesList!!) {
                    if (elements.movieId == movieId) {
                        isFavourite = true
                    }
                }
            }
            callback(isFavourite)
        }

    }

//    fun backgroundFromImage(posterPath: String) {
//        Glide.with(this@DetailsFragment)
//            .asBitmap()
//            .load("${BASE_IMAGE_URL + IMAGE_POSTER_SIZE_BIG + posterPath}")
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap>?
//                ) {
//                    val palette = Palette.from(resource).generate()
//                    val vibrant = palette.darkMutedSwatch
//                    if (vibrant != null)
//                        let { binding.detailLayout.setBackgroundColor(vibrant!!.rgb) }
//
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    onDestroy()
//                }
//
//            })
//    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()

    }

    override fun onItemClick(movieId: Int) {
        TODO("Not yet implemented")
    }


}