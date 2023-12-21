package com.bohunapps.movielistapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bohunapps.movielistapp.databinding.FragmentMoviesListBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Arrays
import java.util.Locale
class MoviesListFragment : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding
    private val moviesList = arrayListOf<MovieEntity>()
    private var adapter: RvAdapter? = null

    private lateinit var viewModel: MoviesListViewModel
    private var currentSortCriterion: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun initList() {
        moviesList.add(
            MovieEntity(
                R.mipmap.tenet,
                "Tenet",
                "Armed with only one word, Tenet, and fighting for the survival of the entire world, a " +
                        "Protagonist journeys through a twilight world of international espionage on a mission that will " +
                        "unfold in something beyond real time.", 7.8,
                "2h 30 min",
                arrayListOf("Action", "Sci-Fi"),
                 "3 September 2020",
                "https://www.youtube.com/watch?v=LdOM0x0XDMo"
            )
        )
        moviesList.add(
            MovieEntity(
                R.mipmap.spider_man,
                "Spider-Man: Into the Spider-Verse ",
                "Teen Miles Morales becomes the Spider-Man of his universe, and must join with five " +
                        "spider-powered individuals from other dimensions to stop a threat for all realities.",
                8.4,
                "1h 57min",
                arrayListOf("Action", "Animation", "Adventure"),
                "14 December 2018",
                "https://www.youtube.com/watch?v=tg52up16eq0"
            )
        )
        moviesList.add(
            MovieEntity(
                R.mipmap.knives_out,
                "Knives Out",
                "A detective investigates the death of a patriarch of an eccentric, combative family.",
                7.9,
                "2h 10min",
                arrayListOf("Comedy", "Crime", "Drama"),
                "27 November 2019",
                "https://www.youtube.com/watch?v=tg52up16eq0"
            )
        )
        moviesList.add(
            MovieEntity(
                R.mipmap.guardians,
                "Guardians of the Galaxy",
                "A group of intergalactic criminals must pull together to stop a fanatical warrior with " +
                        "plans to purge the universe.",
                8.0,
                "2h 1min",
                arrayListOf("Action", "Adventure", "Comedy"),
                "1 August 2014",
                "https://www.youtube.com/watch?v=d96cjJhvlMA"
            )
        )
        moviesList.add(
            MovieEntity(
                R.mipmap.avengers,
                "Avengers: Age of Ultron",
                "When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping " +
                        "program called Ultron, things go horribly wrong and it's up to Earth's mightiest heroes to stop the " +
                        "villainous Ultron from enacting his terrible plan.",
                7.3,
                "2h 21min",
                arrayListOf("Action", "Adventure", "Sci-Fi"),
                "1 May 2015",
                "https://www.youtube.com/watch?v=tmeOjFno6Do"
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (moviesList.isEmpty()) {
            initList()
        }
        viewModel = ViewModelProvider(requireActivity())[MoviesListViewModel::class.java]
        viewModel.setMovies(moviesList)
        viewModel.moviesList.observe(viewLifecycleOwner) { movies ->
            adapter?.updateList(movies)
            adapter?.notifyDataSetChanged()
        }
        adapter = RvAdapter(moviesList)
        binding.rvMovies.adapter = adapter
        binding.tvSort.setOnClickListener {
            showSortDialog()
        }
        currentSortCriterion = viewModel.getCurrentSortCriterion()
        currentSortCriterion?.let {
            viewModel.sortBy(it)
        }
        adapter?.onMovieClicked = {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MovieDetailsFragment.newInstance(it))
                .addToBackStack("MovieDetails").commit()

        }

    }

    private fun showSortDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.sort_dialog, null)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.findViewById<TextView>(R.id.tv_title).setOnClickListener {
            if(currentSortCriterion != TITLE_CRITERION) {
                viewModel.sortBy(TITLE_CRITERION)
                currentSortCriterion = TITLE_CRITERION
            }
            alertDialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.tv_date).setOnClickListener {
            if(currentSortCriterion != DATE_CRITERION) {
                currentSortCriterion = DATE_CRITERION
                viewModel.sortBy(DATE_CRITERION)
            }
            alertDialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


    companion object {
         const val TITLE_CRITERION = "title"
         const val DATE_CRITERION = "date"

        @JvmStatic
        fun newInstance() =
            MoviesListFragment()

    }
}





