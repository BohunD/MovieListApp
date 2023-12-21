package com.bohunapps.movielistapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bohunapps.movielistapp.MoviesListFragment.Companion.DATE_CRITERION
import com.bohunapps.movielistapp.MoviesListFragment.Companion.TITLE_CRITERION


class MoviesListViewModel : ViewModel() {
    private val _moviesList = MutableLiveData<List<MovieEntity>>()
    val moviesList : LiveData<List<MovieEntity>>
        get() = _moviesList
    private var currentSortCriterion: String? = null

    init {
        currentSortCriterion = null
    }

    fun sortBy(criterion: String) {
        currentSortCriterion = criterion
        _moviesList.value = when (criterion) {
            TITLE_CRITERION -> _moviesList.value?.sortedBy { it.title }
            DATE_CRITERION -> _moviesList.value?.sortedBy { it.parseDateString() }?.reversed()
            else -> _moviesList.value
        }
    }

    fun getCurrentSortCriterion(): String? {
        return currentSortCriterion
    }
    fun setMovies(movies:List<MovieEntity> ){
        _moviesList.value = movies
    }
}
