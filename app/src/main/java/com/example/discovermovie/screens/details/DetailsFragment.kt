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
import com.example.discovermovie.data.movieModels.DatabaseMovieModel
import com.example.discovermovie.data.movieModels.details.Genre
import com.example.discovermovie.data.repository.DetailRepository
import com.example.discovermovie.data.repository.LocaleRepository
import com.example.discovermovie.data.repository.RemoteRepository
import com.example.discovermovie.screens.favourite.FavouriteViewModel
import com.example.discovermovie.screens.favourite.FavouriteViewModelProviderFactory
import com.example.discovermovie.screens.home.HomeAdapter
import com.example.discovermovie.util.BASE_IMAGE_URL
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_BIG
import com.example.discovermovie.util.IMAGE_POSTER_SIZE_SMALL
import com.example.discovermovie.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates


class DetailsFragment : Fragment(), HomeAdapter.OnItemClickListener {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var adapter: HomeAdapter
    private var movieId by Delegates.notNull<Int>()

    private lateinit var detailRepository: DetailRepository
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var localeRepository: LocaleRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        adapter = HomeAdapter(this)
        movieId = arguments?.getInt("MovieId") as Int
        val movieDb = MoviesApplication.movieDatabase
        localeRepository = LocaleRepository(movieDb)
        remoteRepository = RemoteRepository()
        detailRepository = DetailRepository(localeRepository, remoteRepository = remoteRepository)
        detailViewModel = viewModels<DetailViewModel> {
            MovieDetailsViewModelProviderFactory(detailRepository = detailRepository)
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
        setUiData()

        detailViewModel.getFavouriteMovies().observe(viewLifecycleOwner) { list ->
            detailViewModel.isFavourite(movieId, list) {
                when (it) {
                    true -> binding.bttAddToFavourite.setImageResource(R.drawable.ic_is_favorite)
                    else -> {}
                }
            }
        }

        binding.bttAddToFavourite.setOnClickListener {
            if (detailViewModel.movieIsInFavourite) {
                detailViewModel.deleteFromFavouriteMovies(movieId)
                binding.bttAddToFavourite.setImageResource(R.drawable.ic_add_favorite)
                detailViewModel.movieIsInFavourite = false
            } else if (!detailViewModel.movieIsInFavourite) {
                addToFavourite()
                binding.bttAddToFavourite.setImageResource(R.drawable.ic_is_favorite)
                detailViewModel.movieIsInFavourite = true
            }
        }


    }


    private fun setUiData() {
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
                        }
                    }
                }
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                else -> {}
            }
        }

    }

    private fun addToFavourite() {
        detailViewModel.movieDetailLiveData.observe(viewLifecycleOwner) { detailResponce ->
            detailResponce.data?.apply {
                val movie = DatabaseMovieModel(
                    id = null,
                    movieId = movieId,
                    original_title = original_title,
                    poster_path = BASE_IMAGE_URL + IMAGE_POSTER_SIZE_BIG + poster_path,
                    status = status,
                    release_date = release_date
                )
                Log.d("DATABASE", movie.original_title)
                detailViewModel.addToFavouriteMovies(movie)
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
            Log.d("IMAGES", it.size.toString())
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