package com.bohunapps.movielistapp

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bohunapps.movielistapp.databinding.FragmentMovieDetailsBinding


class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private var movie: MovieEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(KEY_MOVIE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.llBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        movie?.let { movie ->
            binding.tvMovieName.text = movie.title
            binding.tvGenre.text = movie.showGenre()
            binding.tvDescription.text = movie.description
            binding.tvRating.text = movie.rating.toString()
            binding.ivPhoto.setImageResource(movie.photo)
            changeWatchListBtnText()
            binding.tvAddToWatchlist.setOnClickListener {
                movie.isInWatchList = !movie.isInWatchList
                changeWatchListBtnText()
            }
            binding.tvTrailer.setOnClickListener {
                openYouTubeTrailer(movie.trailerLink)
            }
            binding.tvDate.text = movie.convertDateFormat()
        }
    }

    override fun onStop() {
        super.onStop()
        retainInstance = true
    }

    private fun openYouTubeTrailer(trailerLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setPackage("com.google.android.youtube")
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            intent.setPackage(null)
            startActivity(intent)
        }
    }

    private fun changeWatchListBtnText(){
        movie?.let {
            binding.tvAddToWatchlist.text = if (it.isInWatchList) {
                "REMOVE FROM WATCHLIST"
            } else {
                "+ ADD TO WATCHLIST"
            }
        }
    }

    companion object {
        private const val KEY_MOVIE = "key_movie"

        @JvmStatic
        fun newInstance(movie: MovieEntity) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_MOVIE, movie)
                }
            }
    }
}